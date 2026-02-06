package com.branches.maodeobra.repository;

import com.branches.maodeobra.repository.projections.AnaliseMaoDeObraPorMesProjection;
import com.branches.maodeobra.repository.projections.TotalHoraDeMaoDeObraPorMesProjection;
import com.branches.obra.domain.enums.TipoMaoDeObra;
import com.branches.maodeobra.domain.MaoDeObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MaoDeObraRepository extends JpaRepository<MaoDeObraEntity, Long> {
    List<MaoDeObraEntity> findAllByTenantIdAndTipoAndAtivoIsTrue(Long tenantId, TipoMaoDeObra tipo);

    Optional<MaoDeObraEntity> findByIdAndTenantIdAndAtivoIsTrue(Long id, Long tenantId);

    Optional<MaoDeObraEntity> findByIdAndTenantIdAndTipoAndAtivoIsTrue(Long id, Long tenantId, TipoMaoDeObra type);

    List<MaoDeObraEntity> findAllByIdInAndTenantIdAndTipoAndAtivoIsTrue(Collection<Long> ids, Long tenantId, TipoMaoDeObra tipo);


    @Query("""
    SELECT
        MONTH(r.dataInicio) AS mes,
        COALESCE(SUM(mor.minutosTrabalhados),0) AS totalMinutos
    FROM MaoDeObraDeRelatorioEntity mor
        JOIN mor.relatorio r
        JOIN ObraEntity o ON r.obraId = o.id
        JOIN mor.maoDeObra m
    WHERE m.tenantId = :tenantId
      AND r.ativo = true
      AND m.ativo = true
      AND o.ativo = true
      AND (:maoDeObraId IS NULL OR m.id = :maoDeObraId)
      AND YEAR(r.dataInicio) = :ano
      AND (:obraExternalId IS NULL OR o.idExterno = :obraExternalId)
    GROUP BY MONTH(r.dataInicio)
    ORDER BY MONTH(r.dataInicio)
""")
    List<TotalHoraDeMaoDeObraPorMesProjection> findTotalHoraDeMaoDeObraPorMes(Long tenantId, Integer ano, String obraExternalId, Long maoDeObraId);

    @Query("""
    SELECT
        MONTH(r.dataInicio) AS mes,
        m.id AS maoDeObraId,
        m.nome AS nomeMaoDeObra,
        m.funcao AS funcaoMaoDeObra,
        COALESCE(SUM(mor.minutosTrabalhados), 0) AS totalMinutos,
        COALESCE(COUNT(
            CASE WHEN mor.presenca = 'FALTA' THEN 1 ELSE 0 END
        ), 0) AS totalFaltas,
        COALESCE(COUNT(
            CASE WHEN mor.presenca = 'PRESENTE' THEN 1 ELSE 0 END
        ), 0) AS totalPresentes
    FROM MaoDeObraDeRelatorioEntity mor
    JOIN mor.relatorio r
    JOIN ObraEntity o ON r.obraId = o.id
    JOIN mor.maoDeObra m
    WHERE m.tenantId = :tenantId
        AND r.ativo = true
        AND m.ativo = true
        AND o.ativo = true
        AND YEAR(r.dataInicio) = :year
        AND (:obraExternalId IS NULL OR o.idExterno = :obraExternalId)
        GROUP BY MONTH(r.dataInicio), m.id, m.nome, m.funcao
    """)
    List<AnaliseMaoDeObraPorMesProjection> findQuantidadeMaoDeObraPorMes(Long tenantId, Integer year, String obraExternalId);
}