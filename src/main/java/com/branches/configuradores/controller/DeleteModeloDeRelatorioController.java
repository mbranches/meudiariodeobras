package com.branches.configuradores.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.configuradores.service.DeleteModeloDeRelatorioService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeleteModeloDeRelatorioController {
    private final DeleteModeloDeRelatorioService deleteModeloDeRelatorioService;

    @DeleteMapping("/api/tenants/{tenantExternalId}/modelos-de-relatorio/{modeloId}")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId,
                                        @PathVariable Long modeloId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        deleteModeloDeRelatorioService.execute(modeloId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}

