package com.branches.relatorio.maodeobra.repository;

import com.branches.obra.domain.enums.TipoMaoDeObra;
import com.branches.relatorio.maodeobra.domain.MaoDeObraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaoDeObraRepository extends JpaRepository<MaoDeObraEntity, Long> {
    List<MaoDeObraEntity> findAllByTenantIdAndTipo(Long tenantId, TipoMaoDeObra tipo);
}