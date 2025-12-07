package com.branches.relatorio.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.relatorio.dto.response.RelatorioResponse;
import com.branches.relatorio.service.ListarRelatoriosService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.utils.PageResponse;
import com.branches.utils.PageableRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListarRelatoriosController {
    private final ListarRelatoriosService listarRelatoriosService;

    @GetMapping("/api/tenants/{tenantExternalId}/relatorios")
    public ResponseEntity<PageResponse<RelatorioResponse>> execute(@PathVariable String tenantExternalId,
                                                                   @Valid PageableRequest pageableRequest) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        PageResponse<RelatorioResponse> response = listarRelatoriosService.execute(tenantExternalId, userTenants, pageableRequest);

        return ResponseEntity.ok(response);
    }
}
