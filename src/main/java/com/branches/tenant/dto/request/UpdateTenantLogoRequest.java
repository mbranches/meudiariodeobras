package com.branches.tenant.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateTenantLogoRequest(
        @NotBlank(message = "O campo 'base64Image' é obrigatório")
        String base64Image
) {
}
