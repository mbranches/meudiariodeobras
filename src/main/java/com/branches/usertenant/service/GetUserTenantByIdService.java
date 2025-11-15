package com.branches.usertenant.service;

import com.branches.exception.NotFoundException;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.domain.UserTenantKey;
import com.branches.usertenant.repository.UserTenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUserTenantByIdService {
    private final UserTenantRepository userTenantRepository;

    public UserTenantEntity execute(UserTenantKey id) {
        return userTenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User não encontrado para o tenant"));
    }
}
