package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.OcorrenciaDeRelatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OcorrenciaDeRelatorioRepository extends JpaRepository<OcorrenciaDeRelatorioEntity, Long> {
    List<OcorrenciaDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);

    Optional<OcorrenciaDeRelatorioEntity> findByIdAndRelatorioId(Long id, Long relatorioId);
}