package com.branches.obra.service;

import com.branches.exception.NotFoundException;
import com.branches.obra.domain.ConfiguracaoDeAssinaturaDeRelatorioEntity;
import com.branches.obra.domain.ConfiguracaoRelatoriosEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.request.UpdateConfigDeAssinaturaDeRelatorioDeObraRequest;
import com.branches.relatorio.service.RegenerateTodosOsRelatoriosDeObraService;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateConfiguracaoDeAssinaturaDeRelatorioDeObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetObraByIdExternoAndTenantIdService getObraByIdExternoAndTenantIdService;
    private final CheckIfUserCanEditObraService checkIfUserCanEditObraService;
    private final RegenerateTodosOsRelatoriosDeObraService regenerateTodosOsRelatoriosDeObraService;

    @Transactional
    public void execute(UpdateConfigDeAssinaturaDeRelatorioDeObraRequest request, Long id, String obraExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);
        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        ObraEntity obra = getObraByIdExternoAndTenantIdService.execute(obraExternalId, tenantId);

        checkIfUserCanEditObraService.execute(currentUserTenant, obra.getId());

        ConfiguracaoRelatoriosEntity configuracaoRelatorios = obra.getConfiguracaoRelatorios();
        List<ConfiguracaoDeAssinaturaDeRelatorioEntity> configAssinaturas = configuracaoRelatorios.getConfiguracoesDeAssinaturaDeRelatorio();

        ConfiguracaoDeAssinaturaDeRelatorioEntity configToUpdate = configAssinaturas.stream()
                .filter(config -> config.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Configuração de assinatura não encontrada com o id: " + id));

        configToUpdate.setNomeAssinante(request.nomeAssinante());

        regenerateTodosOsRelatoriosDeObraService.execute(obra);
    }
}
