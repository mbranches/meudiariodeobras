package com.branches.equipamento.repository;

import com.branches.equipamento.domain.EquipamentoEntity;
import com.branches.equipamento.repository.projections.ItemTopEquipamentosProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EquipamentoRepository extends JpaRepository<EquipamentoEntity, Long> {
    Optional<EquipamentoEntity> findByIdAndTenantIdAndAtivoIsTrue(Long id, Long tenantId);

    List<EquipamentoEntity> findAllByTenantIdAndAtivoIsTrue(Long tenantId);

    @Query("""
    SELECT e.id AS id,
    e.descricao AS descricao,
    COALESCE(SUM(
        CASE
            WHEN er.quantidade IS NOT NULL THEN er.quantidade
            ELSE 1
        END
    ), 0) AS quantidadeUso
    FROM EquipamentoEntity e
    JOIN EquipamentoDeRelatorioEntity er ON er.equipamento.id = e.id
    JOIN er.relatorio r
    JOIN ObraEntity o ON r.obraId = o.id
    WHERE r.ativo = true
    AND e.tenantId = :tenantId
    AND e.ativo = true
    AND (:obraExternalId IS NULL OR o.idExterno = :obraExternalId)
    GROUP BY e.id, e.descricao
""")
    Page<ItemTopEquipamentosProjection> findTopEquipamentos(Long tenantId, String obraExternalId, Pageable pageRequest);
}
