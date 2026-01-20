package com.branches.tenant.service;

import com.branches.assinaturadeplano.domain.enums.AssinaturaStatus;
import com.branches.assinaturadeplano.repository.AssinaturaDePlanoRepository;
import com.branches.plano.service.VerifyIfAlreadyHasPeriodoDeTesteService;
import com.branches.shared.email.EmailSender;
import com.branches.shared.email.SendEmailRequest;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.dto.request.CreateTenantRequest;
import com.branches.tenant.dto.response.CreateTenantResponse;
import com.branches.tenant.repository.TenantRepository;
import com.branches.user.domain.UserEntity;
import com.branches.usertenant.domain.Authorities;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import com.branches.usertenant.repository.UserTenantRepository;
import com.branches.utils.ValidateCnpj;
import com.branches.utils.ValidatePhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class CreateTenantService {
    private final TenantRepository tenantRepository;
    private final UserTenantRepository userTenantRepository;
    private final SpringTemplateEngine templateEngine;
    private final EmailSender emailSender;
    private final AssinaturaDePlanoRepository assinaturaDePlanoRepository;
    private final VerifyIfAlreadyHasPeriodoDeTesteService verifyIfAlreadyHasPeriodoDeTesteService;
    private final ValidatePhoneNumber validatePhoneNumber;
    private final ValidateCnpj validateCnpj;
    private final CheckIfDoesntExistsTenantWithTheCnpjService checkIfDoesntExistsTenantWithTheCnpjService;
    private final CheckIfDoesntExistsTenantWithTheTelefoneService checkIfDoesntExistsTenantWithTheTelefoneService;

    public CreateTenantResponse execute(CreateTenantRequest request, UserEntity user) {
        validateFields(request);

        boolean usuarioPossuiTenantQueJaTestouOSistema = verifyIfUserHasTenantThatAlreadyTestedTheSystem(user);
        TenantEntity tenantToSave = TenantEntity.builder()
                .userResponsavelId(user.getId())
                .razaoSocial(request.razaoSocial())
                .nome(request.nome())
                .cnpj(request.cnpj())
                .telefone(request.telefone())
                .segmento(request.segmento())
                .foiCriadoPorUsuarioQueJaTestouOSistema(usuarioPossuiTenantQueJaTestouOSistema)
                .build();

        TenantEntity saved = tenantRepository.save(tenantToSave);

        UserTenantEntity userTenant = UserTenantEntity.builder()
                .user(user)
                .tenantId(saved.getId())
                .cargo(request.cargoResponsavel())
                .perfil(PerfilUserTenant.ADMINISTRADOR)
                .authorities(Authorities.adminAuthorities())
                .build();
        userTenant.setarId();

        userTenantRepository.save(userTenant);

        sendEmail(user.getEmail(), saved);

        return CreateTenantResponse.from(saved);
    }

    private void validateFields(CreateTenantRequest request) {
        validatePhoneNumber.execute(request.telefone());
        validateCnpj.execute(request.cnpj());
        checkIfDoesntExistsTenantWithTheCnpjService.execute(request.cnpj());
        checkIfDoesntExistsTenantWithTheTelefoneService.execute(request.telefone());
    }

    private boolean verifyIfUserHasTenantThatAlreadyTestedTheSystem(UserEntity userEntity) {
        List<Long> tenantIds = userEntity.getTenantsIds();
        boolean tenantsAlreadyTested = verifyIfAlreadyHasPeriodoDeTesteService.execute(tenantIds);

        if (tenantsAlreadyTested) {
            return true;
        }

        return assinaturaDePlanoRepository.existsByTenantIdInAndStatusIn(tenantIds, AssinaturaStatus.getStatusListThatAlreadyHaveActivePlan());
    }

    private void sendEmail(String email, TenantEntity saved) {
        String html = buildHtml(saved);

        SendEmailRequest request = SendEmailRequest.builder()
                .to(email)
                .subject("Sua empresa foi criada com sucesso!")
                .body(html)
                .build();

        emailSender.sendEmail(request, true);
    }

    private String buildHtml(TenantEntity saved) {
        Map<String, Object> variables = Map.ofEntries(
                Map.entry("tenantName", saved.getNome()),
                Map.entry("linkToAccess", "https://app.rdodigital.com.br")
        );
        Context context = new Context();
        context.setVariables(variables);

        return templateEngine.process("email-tenant-created", context);
    }
}
