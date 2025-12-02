package com.branches.atividade.domain.enums;

import lombok.Getter;

@Getter
public enum StatusAtividade {
    PENDENTE("Pendente"),
    EM_ANDAMENTO("Em Andamento"),
    CONCLUIDA("Concluída"),
    SUSPENSA("Suspensa"),
    NAO_INICIADA("Não Iniciada"),
    NAO_EXECUTADA("Não Executada");

    private final String descricao;

    StatusAtividade(String descricao) {
        this.descricao = descricao;
    }
}
