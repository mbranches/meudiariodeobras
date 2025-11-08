package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.response.CreateGrupoDeObraResponse;
import com.branches.obra.service.CreateGrupoDeObraService;
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
public class CreateGrupoDeObraController {
    private final CreateGrupoDeObraService createGrupoDeObraService;

    @PostMapping("/api/tenants/{tenantExternalId}/grupos-de-obra")
    public ResponseEntity<CreateGrupoDeObraResponse> execute(@PathVariable String tenantExternalId, @RequestBody @Valid CreateGrupoDeObraRequest request) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateGrupoDeObraResponse response = createGrupoDeObraService.execute(tenantExternalId, request, userTenants);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
