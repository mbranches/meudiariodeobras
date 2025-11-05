package com.branches.config.security;

import java.util.List;

public class TenantContext {

    private static ThreadLocal<List<Long>> tenants = new ThreadLocal<>();

    public static void setTenantIds(List<Long> tenantIds) {
        tenants.set(tenantIds);
    }

    public static List<Long> getTenantIds() {
        return tenants.get();
    }

    public static void cleanup() {
        tenants.remove();
    }
}
