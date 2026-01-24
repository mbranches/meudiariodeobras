package com.branches.condicaoclimatica.service;

import com.branches.exception.ForbiddenException;
import com.branches.usertenant.domain.UserTenantEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckIfUserCanViewCondicaoClimaticaService {
    public void execute(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.getAuthorities().getItensDeRelatorio().getCondicaoDoClima()) return;

        throw new ForbiddenException();
    }
}
