package com.branches.relatorio.equipamento.repository;

import com.branches.relatorio.equipamento.domain.EquipamentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentoRepository extends JpaRepository<EquipamentoEntity, Long> {
}
