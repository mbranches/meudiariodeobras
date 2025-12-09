package com.branches.configuradores.service;

import com.branches.configuradores.domain.ModeloDeRelatorioEntity;
import com.branches.configuradores.repositorio.ModeloDeRelatorioRepository;
import com.branches.exception.BadRequestException;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class DeleteModeloDeRelatorioService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final CheckIfUserHasAccessToModeloDeRelatorioService checkIfUserHasAccessToModeloDeRelatorioService;
    private final GetModeloDeRelatorioByIdAndTenantIdService getModeloDeRelatorioByIdAndTenantIdService;
    private final ModeloDeRelatorioRepository modeloDeRelatorioRepository;

    public void execute(Long modeloId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);
        checkIfUserHasAccessToModeloDeRelatorioService.execute(currentUserTenant);

        ModeloDeRelatorioEntity modelo = getModeloDeRelatorioByIdAndTenantIdService.execute(modeloId, tenantId);

        if (modelo.getIsDefault()) {
            throw new BadRequestException("Não é possível deletar o modelo padrão");
        }

        modelo.desativar();
        modeloDeRelatorioRepository.save(modelo);
    }
}

