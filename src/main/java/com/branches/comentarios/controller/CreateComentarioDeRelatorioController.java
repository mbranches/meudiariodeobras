package com.branches.comentarios.controller;

import com.branches.comentarios.dto.request.CreateComentarioDeRelatorioRequest;
import com.branches.comentarios.dto.response.CreateComentarioDeRelatorioResponse;
import com.branches.comentarios.service.CreateComentarioDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CreateComentarioDeRelatorioController {
    private final CreateComentarioDeRelatorioService createComentarioDeRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/comentarios")
    public ResponseEntity<CreateComentarioDeRelatorioResponse> execute(
            @RequestBody @Valid CreateComentarioDeRelatorioRequest request,
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId) {

        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateComentarioDeRelatorioResponse response = createComentarioDeRelatorioService.execute(
                request,
                relatorioExternalId,
                tenantExternalId,
                userTenants
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
