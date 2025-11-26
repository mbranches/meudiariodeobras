package com.branches.arquivo.service;

import com.branches.exception.ForbiddenException;
import com.branches.usertenant.domain.UserTenantEntity;
import org.springframework.stereotype.Service;

@Service
public class CheckIfUserCanViewVideosService {
    public void execute(UserTenantEntity userTenant) {
        if (userTenant.getAuthorities().getItensDeRelatorio().getVideos()) return;

        throw new ForbiddenException();
    }
}
