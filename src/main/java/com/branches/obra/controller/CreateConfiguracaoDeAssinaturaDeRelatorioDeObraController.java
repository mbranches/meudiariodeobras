package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.request.CreateConfigDeAssinaturaDeRelatorioDeObraRequest;
import com.branches.obra.dto.response.ConfiguracaoDeAssinaturaDeRelatorioResponse;
import com.branches.obra.service.CreateConfiguracaoDeAssinaturaDeRelatorioDeObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CreateConfiguracaoDeAssinaturaDeRelatorioDeObraController {
    private final CreateConfiguracaoDeAssinaturaDeRelatorioDeObraService createConfiguracaoDeAssinaturaDeRelatorioDeObraService;

    @PostMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}/configuracao-relatorios/assinaturas")
    public ResponseEntity<ConfiguracaoDeAssinaturaDeRelatorioResponse> execute(@RequestBody @Valid CreateConfigDeAssinaturaDeRelatorioDeObraRequest request, @PathVariable String obraExternalId, @PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        var response = createConfiguracaoDeAssinaturaDeRelatorioDeObraService.execute(request, obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
