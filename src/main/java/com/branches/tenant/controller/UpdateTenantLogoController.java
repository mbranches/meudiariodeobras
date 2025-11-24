package com.branches.tenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.tenant.dto.request.UpdateTenantLogoRequest;
import com.branches.tenant.service.UpdateTenantLogoService;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UpdateTenantLogoController {
    private final UpdateTenantLogoService updateTenantLogoService;

    @PatchMapping("/api/tenants/{tenantExternalId}/logo")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId, @RequestBody @Valid UpdateTenantLogoRequest request) {
        List<UserTenantEntity> userTenantEntities = UserTenantsContext.getUserTenants();

        updateTenantLogoService.execute(tenantExternalId, userTenantEntities, request);

        return ResponseEntity.noContent().build();

    }
}
