package com.branches.relatorio.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.relatorio.dto.response.ImprimirRelatorioResponse;
import com.branches.relatorio.service.ImprimirRelatorioService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImprimirRelatorioController {
    private final ImprimirRelatorioService imprimirRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/pdf/imprimir")
    public ResponseEntity<ImprimirRelatorioResponse> execute(@PathVariable String tenantExternalId,
                                                             @PathVariable String relatorioExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        ImprimirRelatorioResponse response = imprimirRelatorioService.execute(relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
