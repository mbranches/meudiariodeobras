package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.request.AdicionaUserToObraRequest;
import com.branches.obra.service.AddUserToObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AddUserToObraController {
    private final AddUserToObraService addUserToObraService;

    @PostMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}/users")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId,
                                        @PathVariable String obraExternalId,
                                        @RequestBody AdicionaUserToObraRequest request) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        addUserToObraService.execute(request, obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
