package com.branches.relatorio.rdo.repository;

import com.branches.relatorio.rdo.domain.RelatorioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioEntity, Long> {
    Optional<RelatorioEntity> findFirstByTenantIdAndObraIdOrderByEnversCreatedDateDesc(Long tenantId, Long obraId);

    long countByTenantIdAndObraId(Long tenantId, Long obraId);

    Optional<RelatorioEntity> findByIdExternoAndTenantId(String relatorioExternalId, Long tenantId);
}