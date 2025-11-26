package com.branches.arquivo.controller;

import com.branches.arquivo.service.DeleteArquivoDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeleteArquivoDeRelatorioController {
    private final DeleteArquivoDeRelatorioService deleteArquivoDeRelatorioService;

    @DeleteMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/arquivos/{arquivoId}")
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId,
                                        @PathVariable String relatorioExternalId,
                                        @PathVariable Long arquivoId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        deleteArquivoDeRelatorioService.execute(arquivoId, relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
