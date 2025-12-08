package com.branches.tenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.tenant.dto.request.UpdateTenantInfosRequest;
import com.branches.tenant.service.UpdateTenantInfosService;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UpdateTenantInfosController {
    private final UpdateTenantInfosService updateTenantInfosService;

    @PutMapping("/api/tenants/{tenantExternalId}")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId, @RequestBody @Valid UpdateTenantInfosRequest request) {
        List<UserTenantEntity> userTenantEntities = UserTenantsContext.getUserTenants();

        updateTenantInfosService.execute(tenantExternalId, userTenantEntities, request);

        return ResponseEntity.noContent().build();

    }
}
