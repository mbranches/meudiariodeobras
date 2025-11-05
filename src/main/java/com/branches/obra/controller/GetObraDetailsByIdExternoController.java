package com.branches.obra.controller;

import com.branches.obra.dto.response.GetObraDetailsByIdExternoResponse;
import com.branches.obra.service.GetObraDetailsByIdExternoService;
import com.branches.auth.model.UserDetailsImpl;
import com.branches.user.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class GetObraDetailsByIdExternoController {
    private final GetObraDetailsByIdExternoService getObraDetailsByIdExternoService;

    @GetMapping("/api/tenants/{tenantExternalId}/obras/{obraExternalId}")
    public ResponseEntity<GetObraDetailsByIdExternoResponse> execute(@PathVariable String tenantExternalId,
                                                                     @PathVariable String obraExternalId,
                                                                     @AuthenticationPrincipal UserDetailsImpl user) {
        List<UserTenantEntity> userTenants = user.getUser().getUserTenantEntities();

        GetObraDetailsByIdExternoResponse response = getObraDetailsByIdExternoService.execute(obraExternalId, tenantExternalId, userTenants);

        return ResponseEntity.ok(response);

    }
}
