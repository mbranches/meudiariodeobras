package com.branches.obra.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.obra.dto.response.ObraByListAllResponse;
import com.branches.obra.service.ListAllObrasService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ListAllObrasController {
    private final ListAllObrasService listAllObrasService;

    @GetMapping("/api/tenants/{tenantExternalId}/obras")
    public ResponseEntity<List<ObraByListAllResponse>> execute(@PathVariable String tenantExternalId) {
        List<UserTenantEntity> userTenants = UserTenantsContext.getUserTenants();

        List<ObraByListAllResponse> response = listAllObrasService.execute(tenantExternalId, userTenants);

        return ResponseEntity.ok(response);
    }
}
