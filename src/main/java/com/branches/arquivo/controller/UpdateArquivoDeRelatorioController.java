package com.branches.arquivo.controller;

import com.branches.arquivo.dto.UpdateArquivoRequest;
import com.branches.arquivo.service.UpdateArquivoDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
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
public class UpdateArquivoDeRelatorioController {
    private final UpdateArquivoDeRelatorioService updateArquivoDeRelatorioService;

    @PutMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/arquivos/{arquivoId}")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateArquivoRequest request, @PathVariable String tenantExternalId,
                                        @PathVariable String relatorioExternalId, @PathVariable Long arquivoId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateArquivoDeRelatorioService.execute(request, arquivoId, relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }

}
