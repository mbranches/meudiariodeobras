package com.branches.atividade.repository;

import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtividadeDeRelatorioRepository extends JpaRepository<AtividadeDeRelatorioEntity, Long> {
    List<AtividadeDeRelatorioEntity> findAllByRelatorioId(Long relatorioId);

    Optional<AtividadeDeRelatorioEntity> findByIdAndRelatorioId(Long id, Long relatorioId);

    @Query("""
        SELECT a
        FROM AtividadeDeRelatorioEntity a
        WHERE a.tenantId = :tenantId
        AND a.relatorio.obraId = :obraId
        AND (:canViewOnlyAprovados = false OR a.relatorio.status = 'APROVADO')
""")
    Page<AtividadeDeRelatorioEntity> findAllByObraIdAndTenantId(Long obraId, Long tenantId, Boolean canViewOnlyAprovados, Pageable pageRequest);

}