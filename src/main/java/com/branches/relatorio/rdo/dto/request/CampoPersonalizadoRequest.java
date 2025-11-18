package com.branches.relatorio.rdo.dto.request;

import com.branches.relatorio.rdo.domain.CampoPersonalizadoEntity;
import jakarta.validation.constraints.NotBlank;

public record CampoPersonalizadoRequest(
        @NotBlank(message = "O campo 'campo' é obrigatório")
        String campo,
        @NotBlank(message = "O campo 'descricao' é obrigatório")
        String descricao
) {
    public CampoPersonalizadoEntity toEntity(Long tenantId) {
        return CampoPersonalizadoEntity.builder()
                .campo(this.campo)
                .descricao(this.descricao)
                .tenantId(tenantId)
                .build();
    }
}
