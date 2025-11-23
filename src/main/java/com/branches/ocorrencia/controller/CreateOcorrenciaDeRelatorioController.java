package com.branches.ocorrencia.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.ocorrencia.dto.request.CreateOcorrenciaDeRelatorioRequest;
import com.branches.ocorrencia.dto.response.CreateOcorrenciaDeRelatorioResponse;
import com.branches.ocorrencia.service.CreateOcorrenciaDeRelatorioService;
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
public class CreateOcorrenciaDeRelatorioController {
    private final CreateOcorrenciaDeRelatorioService createOcorrenciaDeRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/ocorrencias")
    public ResponseEntity<CreateOcorrenciaDeRelatorioResponse> execute(
            @RequestBody @Valid CreateOcorrenciaDeRelatorioRequest request,
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateOcorrenciaDeRelatorioResponse response = createOcorrenciaDeRelatorioService.execute(request, relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
