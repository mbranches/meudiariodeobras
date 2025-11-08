package com.branches.relatorio.equipamento.service;

import com.branches.exception.ForbiddenException;
import com.branches.relatorio.equipamento.domain.EquipamentoEntity;
import com.branches.relatorio.equipamento.dto.request.CreateEquipamentoRequest;
import com.branches.relatorio.equipamento.dto.response.CreateEquipamentoResponse;
import com.branches.relatorio.equipamento.repository.EquipamentoRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateEquipamentoService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final EquipamentoRepository equipamentoRepository;

    public CreateEquipamentoResponse execute(String tenantExternalId, CreateEquipamentoRequest request, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserHasAccessToEquipamentos(currentUserTenant);

        EquipamentoEntity equipamentoEntity = EquipamentoEntity.builder()
                .descricao(request.descricao())
                .tenantId(tenantId)
                .ativo(true)
                .build();

        EquipamentoEntity saved = equipamentoRepository.save(equipamentoEntity);

        return CreateEquipamentoResponse.from(saved);
    }

    private void checkIfUserHasAccessToEquipamentos(UserTenantEntity currentUserTenant) {
        if (!currentUserTenant.getAuthorities().getCadastros().getEquipamentos()) {
            throw new ForbiddenException();
        }
    }
}
