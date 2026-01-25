package com.branches.equipamento.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.equipamento.dto.response.GetItemTopEquipamentosResponse;
import com.branches.equipamento.service.GetTopEquipamentosService;
import com.branches.shared.pagination.PageResponse;
import com.branches.shared.pagination.PageableRequest;
import com.branches.usertenant.domain.UserTenantEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Equipamento")
@RestController
public class GetTopEquipamentosController {
    private final GetTopEquipamentosService getTopEquipamentosService;

    public GetTopEquipamentosController(GetTopEquipamentosService getTopEquipamentosService) {
        this.getTopEquipamentosService = getTopEquipamentosService;
    }

    @Operation(summary = "Get top equipamentos", description = "Lista os equipamentos mais utilizados em relatórios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de equipamentos recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping("/api/tenants/{tenantExternalId}/equipamentos/analisys/top")
    public ResponseEntity<PageResponse<GetItemTopEquipamentosResponse>> execute(
            @Valid PageableRequest pageableRequest,
            @PathVariable String tenantExternalId,
            @RequestParam(required = false) String obraExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        PageResponse<GetItemTopEquipamentosResponse> response = getTopEquipamentosService.execute(
                tenantExternalId,
                obraExternalId,
                userTenants,
                pageableRequest
        );

        return ResponseEntity.ok(response);
    }
}
