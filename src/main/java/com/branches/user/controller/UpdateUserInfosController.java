package com.branches.user.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.user.dto.request.UpdateUserInfosRequest;
import com.branches.user.service.UpdateUserInfosService;
import com.branches.usertenant.domain.UserTenantEntity;
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
public class UpdateUserInfosController {
    private final UpdateUserInfosService updateUserInfosService;

    @PutMapping("/api/tenants/{tenantExternalId}/users/me")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateUserInfosRequest request, @PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateUserInfosService.execute(tenantExternalId, userTenants, request);

        return ResponseEntity.noContent().build();
    }
}
