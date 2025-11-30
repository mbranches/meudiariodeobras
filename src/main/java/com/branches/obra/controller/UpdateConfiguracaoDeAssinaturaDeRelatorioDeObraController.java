package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.request.UpdateConfigDeAssinaturaDeRelatorioDeObraRequest;
import com.branches.obra.service.UpdateConfiguracaoDeAssinaturaDeRelatorioDeObraService;
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
public class UpdateConfiguracaoDeAssinaturaDeRelatorioDeObraController {
    private final UpdateConfiguracaoDeAssinaturaDeRelatorioDeObraService updateConfiguracaoDeAssinaturaDeRelatorioDeObraService;

    @PutMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}/configuracao-relatorios/assinaturas/{id}")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateConfigDeAssinaturaDeRelatorioDeObraRequest request, @PathVariable String obraExternalId, @PathVariable String tenantExternalId, @PathVariable Long id) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateConfiguracaoDeAssinaturaDeRelatorioDeObraService.execute(request, id, obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
