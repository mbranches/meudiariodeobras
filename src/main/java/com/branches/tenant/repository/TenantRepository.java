package com.branches.tenant.repository;

import com.branches.tenant.domain.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {
    @Query("""
            SELECT t.id
            FROM TenantEntity t
            WHERE t.idExterno = :idExterno
                AND t.ativo IS TRUE
""")
    Optional<Long> findTenantIdByIdExternoAndAtivoIsTrue(String idExterno);
}
