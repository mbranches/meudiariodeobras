package com.branches.equipamento.repository.projections;

public interface QuantidadeEquipamentoPorMesProjection {
    Long getEquipamentoId();
    String getEquipamentoDescricao();
    Integer getMes();
    Long getQuantidade();
}
