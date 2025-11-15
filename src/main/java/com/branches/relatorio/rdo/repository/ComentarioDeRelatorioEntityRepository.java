package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.ComentarioDeRelatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioDeRelatorioEntityRepository extends JpaRepository<ComentarioDeRelatorioEntity, Long> {
    List<ComentarioDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);
}