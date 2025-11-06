package com.branches.config.security;

import com.branches.usertenant.domain.UserTenantEntity;

import java.util.List;

public class UserTenantsContext {

    private static ThreadLocal<List<UserTenantEntity>> userTenants = new ThreadLocal<>();

    private static ThreadLocal<Long> userId = new ThreadLocal<>();

    public static List<UserTenantEntity> getUserTenants() {
        return userTenants.get();
    }

    public static List<Long> getTenantIds() {
        List<UserTenantEntity> userTenantEntities = userTenants.get();
        if (userTenantEntities == null) {
            return null;
        }
        return userTenantEntities.stream()
                .map(UserTenantEntity::getTenantId)
                .toList();
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void setUserTenants(List<UserTenantEntity> tenantIds) {
        userTenants.set(tenantIds);
    }

    public static void setUserId(Long id) {
        userId.set(id);
    }

    public static void cleanup() {
        userTenants.remove();
    }
}
