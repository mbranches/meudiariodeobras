package com.branches.tenant.controller;

import com.branches.config.security.UserTenantsContext;
import com.branches.tenant.dto.request.CreateTenantRequest;
import com.branches.tenant.dto.response.CreateTenantResponse;
import com.branches.tenant.service.CreateTenantService;
import com.branches.user.domain.UserEntity;
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
    public ResponseEntity<CreateTenantResponse> execute(@RequestBody @Valid CreateTenantRequest request) {
        UserEntity user = UserTenantsContext.getUser();
        CreateTenantResponse response = createTenantService.execute(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
