package com.branches.usertenant.repository;

import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.UserTenantKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTenantRepository extends JpaRepository<UserTenantEntity, UserTenantKey> {
    long countByTenantIdAndAtivoIsTrue(Long tenantId);

    @Query("""
    SELECT DISTINCT ut
    FROM UserTenantEntity ut
        LEFT JOIN UserObraPermitidaEntity uo ON uo.userTenant.user.id = ut.user.id
            AND uo.userTenant.tenantId = :tenantId
            AND uo.obraId = :obraId
    WHERE ut.tenantId = :tenantId
        AND ut.ativo = true
        AND (ut.perfil = 'ADMINISTRADOR' OR uo.id IS NOT NULL)
""")
    List<UserTenantEntity> findAllByTenantIdAndUserHasAccessToObraId(Long tenantId, Long obraId);
}
