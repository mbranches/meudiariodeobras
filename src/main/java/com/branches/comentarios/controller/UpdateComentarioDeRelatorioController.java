package com.branches.comentarios.controller;

import com.branches.comentarios.service.UpdateComentarioDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
import com.branches.relatorio.dto.request.UpdateComentarioDeRelatorioRequest;
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
public class UpdateComentarioDeRelatorioController {
    private final UpdateComentarioDeRelatorioService updateComentarioDeRelatorioService;

    @PutMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/comentarios/{id}")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateComentarioDeRelatorioRequest request, @PathVariable String tenantExternalId, @PathVariable String relatorioExternalId, @PathVariable Long id) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateComentarioDeRelatorioService.execute(request, id, relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
