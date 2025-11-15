package com.branches.usertenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.dto.request.UpdateUserOfTenantRequest;
import com.branches.usertenant.service.UpdateUserOfTenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UpdateUserOfTenantController {
    private final UpdateUserOfTenantService updateUserOfTenantService;

    @PutMapping("/api/tenants/{tenantExternalId}/users/{userExternalId}")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateUserOfTenantRequest request, @PathVariable String tenantExternalId, @PathVariable String userExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateUserOfTenantService.execute(request, tenantExternalId, userExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
