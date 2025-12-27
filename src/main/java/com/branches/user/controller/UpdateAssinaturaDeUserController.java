package com.branches.user.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.user.domain.UserEntity;
import com.branches.user.dto.request.UpdateAssinaturaDeUserRequest;
import com.branches.user.service.UpdateAssinaturaDeUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "User")
public class UpdateAssinaturaDeUserController {
    private final UpdateAssinaturaDeUserService updateAssinaturaDeUserService;

    @Operation(summary = "Update user assinatura", description = "Atualiza a assinatura do usuário logado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Assinatura atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PutMapping("/api/users/me/assinatura")
    public ResponseEntity<Void> execute(@Valid @RequestBody UpdateAssinaturaDeUserRequest request) {
        UserEntity user = UserTenantsContext.getUser();

        updateAssinaturaDeUserService.execute(request, user);

        return ResponseEntity.noContent().build();
    }
}
