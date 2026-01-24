package com.branches.condicaoclimatica.controller;

import com.branches.condicaoclimatica.dto.response.CondicaoClimaticaAnalisysResponse;
import com.branches.condicaoclimatica.service.GetCondicaoClimaticaAnalysisService;
import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "CondicaoClimatica")
@RequiredArgsConstructor
@RestController
public class GetCondicaoClimaticaAnalysisController {
    private final GetCondicaoClimaticaAnalysisService getCondicaoClimaticaAnalysisService;

    @Operation(summary = "Get condição climática analysis", description = "Obtém a análise da condição climática para um tenant e, opcionalmente, para uma obra específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Análise da condição climática obtida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/api/tenants/{tenantExternalId}/analysis/condicao-climatica")
    public ResponseEntity<CondicaoClimaticaAnalisysResponse> execute(
            @PathVariable String tenantExternalId,
            @RequestParam(required = false) String obraExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CondicaoClimaticaAnalisysResponse response = getCondicaoClimaticaAnalysisService.execute(tenantExternalId, obraExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
