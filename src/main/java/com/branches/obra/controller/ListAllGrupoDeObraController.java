package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.response.GrupoDeObraResponse;
import com.branches.obra.service.ListAllGrupoDeObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListAllGrupoDeObraController {
    private final ListAllGrupoDeObraService listAllGrupoDeObraService;

    @GetMapping("/api/tenants/{tenantExternalId}/grupos-de-obra")
    public ResponseEntity<List<GrupoDeObraResponse>> execute(@PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        List<GrupoDeObraResponse> response = listAllGrupoDeObraService.execute(tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}