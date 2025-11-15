package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.MaoDeObraDeRelatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaoDeObraDeRelatorioRepository extends JpaRepository<MaoDeObraDeRelatorioEntity, Long> {
    List<MaoDeObraDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);
}