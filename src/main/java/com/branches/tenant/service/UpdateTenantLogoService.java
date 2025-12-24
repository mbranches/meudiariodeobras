package com.branches.tenant.service;

import com.branches.exception.ForbiddenException;
import com.branches.external.aws.S3UploadFile;
import com.branches.obra.domain.LogoDeRelatorioEntity;
import com.branches.relatorio.repository.LogoDeRelatorioRepository;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.dto.request.UpdateTenantLogoRequest;
import com.branches.tenant.repository.TenantRepository;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.enums.PerfilUserTenant;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import com.branches.utils.CompressImage;
import com.branches.utils.FileContentType;
import com.branches.utils.ImageOutPutFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UpdateTenantLogoService {
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetTenantByIdExternoService getTenantByIdExternoService;
    private final CompressImage compressImage;
    private final S3UploadFile s3UploadFile;
    private final TenantRepository tenantRepository;
    private final LogoDeRelatorioRepository logoDeRelatorioRepository;

    public void execute(String tenantExternalId, List<UserTenantEntity> userTenants, UpdateTenantLogoRequest request) {
        TenantEntity tenant = getTenantByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenant.getId());

        checkIfUserCanUpdateTenantLogo(currentUserTenant);

        String base64Image = request.base64Image();
        String fileName = "logo-%s-%s.png".formatted(tenantExternalId, LocalDateTime.now());

        byte[] bytes = compressImage.execute(base64Image, 1000, 400, 0.7, ImageOutPutFormat.PNG);

        String logoUrl = s3UploadFile.execute(fileName, "tenants/%s/logo".formatted(tenantExternalId), bytes, FileContentType.PNG);

        tenant.setLogoUrl(logoUrl);

        tenantRepository.save(tenant);

        updateLogoDoTenantDeConfigDeRelatorio(tenant.getId(), logoUrl);
    }

    private void updateLogoDoTenantDeConfigDeRelatorio(Long id, String logoUrl) {
        List<LogoDeRelatorioEntity> logos = logoDeRelatorioRepository.findAllByTenantIdAndIsLogoDoTenantIsTrue(id);

        List<LogoDeRelatorioEntity> logosToSave = logos.stream()
                .peek(logo -> {
                    logo.setUrl(logoUrl);
                    logo.setExibir(true);
                })
                .toList();

        logoDeRelatorioRepository.saveAll(logosToSave);
    }

    private void checkIfUserCanUpdateTenantLogo(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.getPerfil().equals(PerfilUserTenant.ADMINISTRADOR)) return;

        throw new ForbiddenException();
    }
}
