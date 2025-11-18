package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.EquipamentoDeRelatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface EquipamentoDeRelatorioRepository extends JpaRepository<EquipamentoDeRelatorioEntity, Long> {
    List<EquipamentoDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);

    void removeAllByRelatorioId(Long relatorioId);

    List<EquipamentoDeRelatorioEntity> findAllByIdInAndRelatorioId(Collection<Long> id, Long relatorio_id);

    void removeAllByIdNotInAndRelatorioId(Collection<Long> ids, Long relatorioId);
}