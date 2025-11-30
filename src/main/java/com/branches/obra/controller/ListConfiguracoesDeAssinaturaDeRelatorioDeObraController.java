package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.response.ConfiguracaoDeAssinaturaDeRelatorioResponse;
import com.branches.obra.service.ListConfiguracoesDeAssinaturaDeRelatorioDeObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListConfiguracoesDeAssinaturaDeRelatorioDeObraController {
    private final ListConfiguracoesDeAssinaturaDeRelatorioDeObraService listConfiguracoesDeAssinaturaDeRelatorioDeObraService;

    @GetMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}/configuracao-relatorios/assinaturas")
    public ResponseEntity<List<ConfiguracaoDeAssinaturaDeRelatorioResponse>> execute(@PathVariable String obraExternalId, @PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        List<ConfiguracaoDeAssinaturaDeRelatorioResponse> response = listConfiguracoesDeAssinaturaDeRelatorioDeObraService.execute(obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
