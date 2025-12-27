package com.branches.relatorio.service;

import com.branches.relatorio.dto.response.RelatorioResponse;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.RelatorioProjection;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import com.branches.shared.pagination.PageResponse;
import com.branches.shared.pagination.PageableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ListarRelatoriosService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final RelatorioRepository relatorioRepository;

    public PageResponse<RelatorioResponse> execute(String tenantExternalId, List<UserTenantEntity> userTenants, PageableRequest pageableRequest) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);
        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        Boolean canViewOnlyAprovados = currentUserTenant.getAuthorities().getRelatorios().getCanViewOnlyAprovados();

        PageRequest pageRequest = PageRequest.of(
                pageableRequest.pageNumber(),
                pageableRequest.pageSize(),
                pageableRequest.sortDirection(),
                "dataInicio",
                "enversCreatedDate"
        );

        Page<RelatorioProjection> relatorios = canViewOnlyAprovados ? relatorioRepository.findAllByTenantIdAndIsAprovadoAndUserAccessToTheObraPai(tenantId, currentUserTenant.getObrasPermitidasIds(), currentUserTenant.isAdministrador(), pageRequest)
                : relatorioRepository.findAllByTenantIdAndUserAccessToTheObraPai(tenantId, currentUserTenant.getObrasPermitidasIds(), currentUserTenant.isAdministrador(), pageRequest);

        Page<RelatorioResponse> response = relatorios.map(RelatorioResponse::from);

        return PageResponse.from(response);
    }
}
