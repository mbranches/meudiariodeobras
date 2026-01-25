package com.branches.equipamento.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.equipamento.dto.response.AnaliseDeEquipamentosPorMesResponse;
import com.branches.equipamento.service.GetAnaliseEquipamentosPorMesService;
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

@RequiredArgsConstructor
@RestController
@Tag(name = "Equipamento")
public class GetAnaliseEquipamentosPorMesController {
    private final GetAnaliseEquipamentosPorMesService getAnaliseEquipamentosPorMesService;

    @Operation(summary = "Get quantidade de equipamento por mês durante um ano", description = "Obtém a quantidade de equipamentos utilizados por mês durante um ano")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Quantidade de equipamentos por mês obtida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/api/tenants/{tenantExternalId}/equipamentos/analisys/quantidade-por-mes")
    public ResponseEntity<AnaliseDeEquipamentosPorMesResponse> execute(
            @PathVariable String tenantExternalId,
            @RequestParam Integer year,
            @RequestParam(required = false) String obraExternalId,
            @RequestParam(required = false) Long equipamentoIdPraFiltrarTotalPorMes
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        AnaliseDeEquipamentosPorMesResponse response = getAnaliseEquipamentosPorMesService.execute(
                tenantExternalId,
                year,
                obraExternalId,
                equipamentoIdPraFiltrarTotalPorMes,
                userTenants
        );

        return ResponseEntity.ok(response);
    }
}
