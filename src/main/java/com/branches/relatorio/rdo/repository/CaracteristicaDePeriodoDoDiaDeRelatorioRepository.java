package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.CaracteristicaDePeriodoDoDiaDeRelatorioEntity;
import com.branches.relatorio.rdo.domain.CondicaoDeTempoDeRelatorioKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaDePeriodoDoDiaDeRelatorioRepository extends JpaRepository<CaracteristicaDePeriodoDoDiaDeRelatorioEntity, CondicaoDeTempoDeRelatorioKey> {
    List<CaracteristicaDePeriodoDoDiaDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);
}