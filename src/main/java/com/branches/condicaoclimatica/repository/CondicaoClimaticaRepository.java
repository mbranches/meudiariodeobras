package com.branches.condicaoclimatica.repository;

import com.branches.condicaoclimatica.domain.CondicaoClimaticaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CondicaoClimaticaRepository extends JpaRepository<CondicaoClimaticaEntity, Long> {
    Optional<CondicaoClimaticaEntity> findByIdAndTenantId(Long id, Long tenantId);
}