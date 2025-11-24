package com.branches.tenant.service;

import com.branches.exception.ForbiddenException;
import com.branches.external.aws.S3UploadFile;
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

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateTenantLogoService {
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetTenantByIdExternoService getTenantByIdExternoService;
    private final CompressImage compressImage;
    private final S3UploadFile s3UploadFile;
    private final TenantRepository tenantRepository;

    public void execute(String tenantExternalId, List<UserTenantEntity> userTenants, UpdateTenantLogoRequest request) {
        TenantEntity tenant = getTenantByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenant.getId());

        checkIfUserCanUpdateTenantLogo(currentUserTenant);

        String base64Image = request.base64Image();
        String fileName = "logo-%s.png".formatted(tenantExternalId);

        byte[] bytes = compressImage.execute(base64Image, 1000, 400, 0.7, ImageOutPutFormat.PNG);

        String logoUrl = s3UploadFile.execute(fileName, "tenants/%s/logo".formatted(tenantExternalId), bytes, FileContentType.PNG);

        tenant.setLogoUrl(logoUrl);

        tenantRepository.save(tenant);
    }

    private void checkIfUserCanUpdateTenantLogo(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.getPerfil().equals(PerfilUserTenant.ADMINISTRADOR)) return;

        throw new ForbiddenException();
    }
}
