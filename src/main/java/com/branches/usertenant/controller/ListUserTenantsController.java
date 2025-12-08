package com.branches.usertenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.dto.response.UserTenantResponse;
import com.branches.usertenant.service.ListUserTenantsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListUserTenantsController {
    private final ListUserTenantsService listUserTenantsService;

    @GetMapping("/api/tenants/{tenantExternalId}/users")
    public ResponseEntity<List<UserTenantResponse>> execute(
            @PathVariable String tenantExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        List<UserTenantResponse> response = listUserTenantsService.execute(tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
