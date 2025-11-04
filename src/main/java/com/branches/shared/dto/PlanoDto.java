package com.branches.shared.dto;

import com.branches.plano.domain.PlanoEntity;

import java.math.BigDecimal;

public record PlanoDto(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer limiteUsuarios,
        Integer limiteObras
) {
    public static PlanoDto from(PlanoEntity plano) {
        return new PlanoDto(
                plano.getId(),
                plano.getNome(),
                plano.getDescricao(),
                plano.getPreco(),
                plano.getLimiteUsuarios(),
                plano.getLimiteObras()
        );
    }
}
