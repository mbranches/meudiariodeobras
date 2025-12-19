package com.branches.usertenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.dto.response.UserTenantResponse;
import com.branches.usertenant.service.GetUserTenantByUserIdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetUserTenantByUserIdController {
    private final GetUserTenantByUserIdService getUserTenantByUserIdService;

    @GetMapping("/api/tenants/{tenantExternalId}/users/{userExternalId}")
    public ResponseEntity<UserTenantResponse> execute(@PathVariable String tenantExternalId, @PathVariable String userExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        UserTenantResponse response = getUserTenantByUserIdService.execute(tenantExternalId, userExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
