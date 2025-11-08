package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.request.UpdateGrupoDeObraRequest;
import com.branches.obra.service.UpdateGrupoDeObraService;
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
public class UpdateGrupoDeObraController {
    private final UpdateGrupoDeObraService updateGrupoDeObraService;

    @PutMapping("/api/tenants/{tenantExternalId}/grupos-de-obra/{id}")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId, @PathVariable Long id, @RequestBody @Valid UpdateGrupoDeObraRequest request) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateGrupoDeObraService.execute(id, tenantExternalId, request, userTenants);

        return ResponseEntity.noContent().build();
    }
}
