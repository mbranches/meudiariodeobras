package com.branches.arquivo.service;

import com.branches.exception.ForbiddenException;
import com.branches.usertenant.domain.UserTenantEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckIfUserCanAddFotosToRelatorioService {
    public void execute(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.getAuthorities().getRelatorios().getCanAddFotos()) return;

        throw new ForbiddenException();
    }
}
