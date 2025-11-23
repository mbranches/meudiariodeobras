package com.branches.maodeobra.controller;

import com.branches.maodeobra.dto.request.CreateMaoDeObraDeRelatorioRequest;
import com.branches.maodeobra.dto.response.CreateMaoDeObraDeRelatorioResponse;
import com.branches.maodeobra.service.CreateMaoDeObraDeRelatorioService;
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
public class CreateMaoDeObraDeRelatorioController {
    private final CreateMaoDeObraDeRelatorioService createMaoDeObraDeRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/maodeobra")
    public ResponseEntity<CreateMaoDeObraDeRelatorioResponse> execute(
            @RequestBody @Valid CreateMaoDeObraDeRelatorioRequest request,
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId) {

        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateMaoDeObraDeRelatorioResponse response = createMaoDeObraDeRelatorioService.execute(
                request,
                relatorioExternalId,
                tenantExternalId,
                userTenants
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
