package com.branches.arquivo.controller;

import com.branches.arquivo.dto.request.CreateVideoDeRelatorioRequest;
import com.branches.arquivo.dto.response.CreateVideoDeRelatorioResponse;
import com.branches.arquivo.service.CreateVideoDeRelatorioService;
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
public class CreateVideoDeRelatorioController {
    private final CreateVideoDeRelatorioService createVideoDeRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/videos")
    public ResponseEntity<CreateVideoDeRelatorioResponse> execute(
            @RequestBody @Valid CreateVideoDeRelatorioRequest request,
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateVideoDeRelatorioResponse response = createVideoDeRelatorioService.execute(
                request,
                tenantExternalId,
                relatorioExternalId,
                userTenants
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

