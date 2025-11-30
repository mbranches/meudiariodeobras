package com.branches.obra.service;

import com.branches.obra.domain.ConfiguracaoDeAssinaturaDeRelatorioEntity;
import com.branches.obra.domain.ConfiguracaoRelatoriosEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.response.ConfiguracaoDeAssinaturaDeRelatorioResponse;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListConfiguracoesDeAssinaturaDeRelatorioDeObraService {
    private final CheckIfUserCanEditObraService checkIfUserCanEditObraService;
    private final GetObraByIdExternoAndTenantIdService getObraByIdExternoAndTenantIdService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;

    @Transactional
    public List<ConfiguracaoDeAssinaturaDeRelatorioResponse> execute(String obraExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);
        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        ObraEntity obra = getObraByIdExternoAndTenantIdService.execute(obraExternalId, tenantId);

        checkIfUserCanEditObraService.execute(currentUserTenant, obra.getId());

        ConfiguracaoRelatoriosEntity configuracaoRelatorios = obra.getConfiguracaoRelatorios();

        List<ConfiguracaoDeAssinaturaDeRelatorioEntity> response = configuracaoRelatorios.getConfiguracoesDeAssinaturaDeRelatorio();

        return response.stream()
                .map(ConfiguracaoDeAssinaturaDeRelatorioResponse::from)
                .toList();
    }
}
