package com.branches.maodeobra.controller;

import com.branches.maodeobra.dto.response.MaoDeObraDeRelatorioResponse;
import com.branches.maodeobra.service.ListMaoDeObraDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListMaoDeObraDeRelatorioController {
    private final ListMaoDeObraDeRelatorioService listMaoDeObraDeRelatorioService;

    @GetMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/maodeobra")
    public ResponseEntity<List<MaoDeObraDeRelatorioResponse>> execute(
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        List<MaoDeObraDeRelatorioResponse> response = listMaoDeObraDeRelatorioService.execute(relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
