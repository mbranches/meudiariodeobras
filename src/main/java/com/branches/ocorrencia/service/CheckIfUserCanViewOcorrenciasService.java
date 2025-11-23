package com.branches.ocorrencia.service;

import com.branches.exception.ForbiddenException;
import com.branches.usertenant.domain.UserTenantEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckIfUserCanViewOcorrenciasService {
    public void execute(UserTenantEntity userTenant) {
        if (userTenant.getAuthorities().getItensDeRelatorio().getOcorrencias()) return;

        throw new ForbiddenException();
    }
}
