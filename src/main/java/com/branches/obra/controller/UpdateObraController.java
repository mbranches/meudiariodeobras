package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.request.UpdateObraRequest;
import com.branches.obra.service.UpdateObraService;
import com.branches.usertenant.domain.UserTenantEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UpdateObraController {
    private final UpdateObraService updateObraService;

    @PutMapping("/api/tenants/{tenantExternalId}/obras/{obraId}")
    public ResponseEntity<Void> execute(@RequestBody @Valid UpdateObraRequest request, @PathVariable String obraId, @PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        updateObraService.execute(request, obraId, tenantExternalId, userTenants);

        return ResponseEntity.noContent().build();
    }
}
