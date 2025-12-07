package com.branches.comentarios.service;

import com.branches.exception.ForbiddenException;
import com.branches.usertenant.domain.UserTenantEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckIfUserCanAddComentariosToRelatorioService {
    public void execute(UserTenantEntity userTenant) {
        if (userTenant.getAuthorities().getRelatorios().getCanAddComentarios()) return;

        throw new ForbiddenException();
    }
}
