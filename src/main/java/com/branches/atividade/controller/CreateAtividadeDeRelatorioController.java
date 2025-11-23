package com.branches.atividade.controller;

import com.branches.atividade.dto.request.CreateAtividadeDeRelatorioRequest;
import com.branches.atividade.dto.response.CreateAtividadeDeRelatorioResponse;
import com.branches.atividade.service.CreateAtividadeDeRelatorioService;
import com.branches.config.security.UserTenantsContext;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CreateAtividadeDeRelatorioController {
    private final CreateAtividadeDeRelatorioService createAtividadeDeRelatorioService;

    @PostMapping("/api/tenants/{tenantExternalId}/relatorios/{relatorioExternalId}/atividades")
    public ResponseEntity<CreateAtividadeDeRelatorioResponse> execute(
            @RequestBody @Valid CreateAtividadeDeRelatorioRequest request,
            @PathVariable String tenantExternalId,
            @PathVariable String relatorioExternalId
    ) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        CreateAtividadeDeRelatorioResponse response = createAtividadeDeRelatorioService.execute(request, relatorioExternalId, tenantExternalId, userTenants);

        return ResponseEntity.ok().body(response);
    }
}
