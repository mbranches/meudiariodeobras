package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.service.DeleteConfiguracaoDeAssinaturaDeRelatorioDeObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class DeleteConfiguracaoDeAssinaturaDeRelatorioDeObraController {
    private final DeleteConfiguracaoDeAssinaturaDeRelatorioDeObraService deleteConfiguracaoDeAssinaturaDeRelatorioDeObraService;

    @DeleteMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}/configuracao-relatorios/assinaturas/{id}")
    public ResponseEntity<Void> execute(@PathVariable String obraExternalId, @PathVariable String tenantExternalId, @PathVariable Long id) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        deleteConfiguracaoDeAssinaturaDeRelatorioDeObraService.execute(id, obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
