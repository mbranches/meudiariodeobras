package com.branches.plano.port;

import com.branches.plano.domain.PlanoEntity;

public interface LoadPlanoPort {
    PlanoEntity getPlanoById(Long planoId);
}
