package com.branches.tenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.tenant.dto.request.CreateTenantRequest;
import com.branches.tenant.dto.response.CreateTenantResponse;
import com.branches.tenant.service.CreateTenantService;
import com.branches.user.domain.UserEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CreateTenantController {
    private final CreateTenantService createTenantService;

    @PostMapping("/api/tenants")
    @Operation(summary = "Create tenant", description = "Cria tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tenant criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Tenant não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<CreateTenantResponse> execute(@RequestBody @Valid CreateTenantRequest request) {
        UserEntity user = UserTenantsContext.getUser();
        CreateTenantResponse response = createTenantService.execute(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
