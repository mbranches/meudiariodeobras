package com.branches.tenant.repository;

import com.branches.tenant.domain.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    Optional<TenantEntity> findByIdExternoAndAtivoIsTrue(String idExterno);

    Optional<TenantEntity> findByIdAndAtivoIsTrue(Long id);
}
