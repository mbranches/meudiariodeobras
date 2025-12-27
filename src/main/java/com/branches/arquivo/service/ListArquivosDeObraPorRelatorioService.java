package com.branches.arquivo.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.arquivo.domain.enums.TipoArquivo;
import com.branches.arquivo.dto.response.ArquivosDeObraPorRelatorioResponse;
import com.branches.arquivo.repository.ArquivoRepository;
import com.branches.exception.InternalServerError;
import com.branches.obra.controller.CheckIfUserHasAccessToObraService;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.service.GetObraByIdExternoAndTenantIdService;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.shared.pagination.PageResponse;
import com.branches.shared.pagination.PageableRequest;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ListArquivosDeObraPorRelatorioService {

    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetObraByIdExternoAndTenantIdService getObraByIdExternoAndTenantIdService;
    private final CheckIfUserHasAccessToObraService checkIfUserHasAccessToObraService;
    private final CheckIfUserCanViewFotosService checkIfUserCanViewFotosService;
    private final CheckIfUserCanViewVideosService checkIfUserCanViewVideosService;
    private final ArquivoRepository arquivoRepository;

    public PageResponse<ArquivosDeObraPorRelatorioResponse> execute(String tenantExternalId, String obraExternalId, TipoArquivo tipo, List<UserTenantEntity> userTenants, PageableRequest pageableRequest) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        ObraEntity obra = getObraByIdExternoAndTenantIdService.execute(obraExternalId, tenantId);

        checkIfUserHasAccessToObraService.execute(currentUserTenant, obra.getId());
        switch (tipo) {
            case FOTO -> checkIfUserCanViewFotosService.execute(currentUserTenant);
            case VIDEO -> checkIfUserCanViewVideosService.execute(currentUserTenant);
            default -> throw new InternalServerError("Tipo de arquivo não suportado");
        }

        PageRequest pageRequest = pageableRequest.toPageRequest("enversCreatedDate");
        Boolean canViewOnlyAprovados = currentUserTenant.getAuthorities().getRelatorios().getCanViewOnlyAprovados();

        Page<ArquivoEntity> arquivos = canViewOnlyAprovados ? arquivoRepository.findAllByObraIdAndTipoArquivoAndTenantIdAndRelatorioIsAprovado(obra.getId(), tipo, tenantId, pageRequest)
                : arquivoRepository.findAllByObraIdAndTipoArquivoAndTenantId(obra.getId(), tipo, tenantId, pageRequest);

        Map<RelatorioEntity, List<ArquivoEntity>> MapRelatorioAndArquivos = arquivos.stream()
                .collect(Collectors.groupingBy(ArquivoEntity::getRelatorio));

        List<ArquivosDeObraPorRelatorioResponse> content = MapRelatorioAndArquivos.entrySet().stream()
                .map(entry -> ArquivosDeObraPorRelatorioResponse.from(entry.getKey(), entry.getValue()))
                .toList();

        return new PageResponse<>(
                arquivos.getSize(),
                arquivos.getNumber(),
                arquivos.getTotalElements(),
                content,
                arquivos.isFirst(),
                arquivos.isLast()
        );
    }
}
