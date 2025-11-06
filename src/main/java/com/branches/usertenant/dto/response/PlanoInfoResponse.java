package com.branches.usertenant.dto.response;

import com.branches.plano.domain.PlanoEntity;

import java.math.BigDecimal;

public record PlanoInfoResponse(
        Long id,
        String nome,
        String descricao,
        BigDecimal valor,
        Integer limiteUsuarios,
        Integer limiteObras) {
    public static PlanoInfoResponse from(PlanoEntity plano) {
        return new PlanoInfoResponse(
                plano.getId(),
                plano.getNome(),
                plano.getDescricao(),
                plano.getValor(),
                plano.getLimiteUsuarios(),
                plano.getLimiteObras()
        );
    }
}
