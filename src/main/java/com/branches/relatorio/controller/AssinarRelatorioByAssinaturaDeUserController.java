package com.branches.relatorio.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.relatorio.service.AssinarRelatorioByAssinaturaDeUserService;
import com.branches.user.domain.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "Relatorio")
public class AssinarRelatorioByAssinaturaDeUserController {
    private final AssinarRelatorioByAssinaturaDeUserService assinarRelatorioByAssinaturaDeUserService;

    @PutMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/assinaturas/{id}/assinar-via-assinatura-de-user")
    @Operation(summary = "Assinar relatorio via assinatura do user", description = "Assina um relatório pela assinatura do user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Relatório assinado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Relatório não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> execute(@PathVariable String tenantExternalId, @PathVariable String relatorioExternalId, @PathVariable Long id) {
        UserEntity user = UserTenantsContext.getUser();

        assinarRelatorioByAssinaturaDeUserService.execute(id, relatorioExternalId, tenantExternalId, user);

        return ResponseEntity.noContent().build();
    }
}
