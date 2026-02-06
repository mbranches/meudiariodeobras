package com.branches.maodeobra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.maodeobra.dto.response.AnaliseDeMaoDeObraPorMesResponse;
import com.branches.maodeobra.service.GetAnaliseMaoDeObraPorMesService;
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

@Tag(name = "MaoDeObra")
@RequiredArgsConstructor
@RestController
public class GetAnaliseMaoDeObraPorMesController {
    private final GetAnaliseMaoDeObraPorMesService getAnaliseMaoDeObraPorMesService;

    @Operation(summary = "Get analise de mao de obra por mês durante um ano", description = "Obtém a analise de mao de obra utilizadas por mês durante um ano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analise de mao de obra por mês obtida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/api/tenants/{tenantExternalId}/mao-de-obra/analysis/quantidade-por-mes")
    public ResponseEntity<AnaliseDeMaoDeObraPorMesResponse> execute(
            @PathVariable String tenantExternalId,
            @RequestParam Integer year,
            @RequestParam(required = false) String obraExternalId,
            @RequestParam(required = false) Long maoDeoObraIdPraFiltrarTotalPorMes
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        AnaliseDeMaoDeObraPorMesResponse response = getAnaliseMaoDeObraPorMesService.execute(tenantExternalId, year, obraExternalId, maoDeoObraIdPraFiltrarTotalPorMes, userTenants);

        return ResponseEntity.ok(response);
    }
}
