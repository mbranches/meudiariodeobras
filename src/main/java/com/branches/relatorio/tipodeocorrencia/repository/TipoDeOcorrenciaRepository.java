package com.branches.relatorio.tipodeocorrencia.repository;

import com.branches.relatorio.tipodeocorrencia.domain.TipoDeOcorrenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TipoDeOcorrenciaRepository extends JpaRepository<TipoDeOcorrenciaEntity, Long> {
    Optional<TipoDeOcorrenciaEntity> findByIdAndTenantIdAndAtivoIsTrue(Long id, Long tenantId);

    List<TipoDeOcorrenciaEntity> findAllByTenantIdAndAtivoIsTrue(Long tenantId);
}
