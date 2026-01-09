package com.branches.assinaturadeplano.service;

import com.branches.assinaturadeplano.domain.AssinaturaDePlanoEntity;
import com.branches.assinaturadeplano.repository.AssinaturaDePlanoRepository;
import com.branches.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.branches.assinaturadeplano.domain.enums.AssinaturaStatus.*;

@RequiredArgsConstructor
@Service
public class GetAssinaturaActiveByTenantIdService {
    private final AssinaturaDePlanoRepository assinaturaDePlanoRepository;

    public AssinaturaDePlanoEntity execute(Long tenantId) {
        return assinaturaDePlanoRepository.findByStatusInAndTenantId(List.of(ATIVO, VENCIDO), tenantId)
                .orElseThrow(() -> new NotFoundException("Assinatura ativa não encontrada para o tenant"));
    }
}
