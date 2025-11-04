package com.branches.plano.adapter;

import com.branches.plano.domain.PlanoEntity;
import com.branches.plano.port.LoadPlanoPort;
import com.branches.plano.repositroy.PlanoRepository;
import com.branches.shared.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PlanoPersistenceAdapter implements LoadPlanoPort {
    private final PlanoRepository planoRepository;

    @Override
    public PlanoEntity getPlanoById(Long planoId) {
        return planoRepository.findById(planoId)
                .orElseThrow(() -> new NotFoundException("Plano não encontrado com o id: " + planoId));
    }
}
