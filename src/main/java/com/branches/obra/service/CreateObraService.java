package com.branches.obra.service;

import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.request.CreateObraRequest;
import com.branches.obra.dto.response.CreateObraResponse;
import com.branches.obra.port.LoadObraPort;
import com.branches.obra.port.WriteObraPort;
import com.branches.plano.service.GetPlanoAtivoByTenantIdService;
import com.branches.shared.dto.PlanoDto;
import com.branches.shared.exception.BadRequestException;
import com.branches.shared.exception.ForbiddenException;
import com.branches.shared.dto.TenantDto;
import com.branches.tenant.service.GetTenantByIdExternoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateObraService {
    private final WriteObraPort writeObra;
    private final GetTenantByIdExternoService getTenantByIdExternoService;
    private final GetPlanoAtivoByTenantIdService getPlanoAtivoByTenantIdService;
    private final LoadObraPort loadObraPort;

    public CreateObraResponse execute(CreateObraRequest request, String tenantDaObraExternalId, List<Long> requestingUserTenantIds) {
        TenantDto tenant = getTenantByIdExternoService.execute(tenantDaObraExternalId);
        Long tenantId = tenant.id();

        if (!requestingUserTenantIds.contains(tenantId)) {
            throw new ForbiddenException();
        }

        verifyIfPlanoAllowsCreateObra(tenantId);

        //TODO: verificar se o user tem permissao para criar obra

        ObraEntity obraToSave = ObraEntity.builder()
                .nome(request.nome())
                .responsavel(request.responsavel())
                .contratante(request.contratante())
                .tipoContrato(request.tipoContrato())
                .dataInicio(request.dataInicio())
                .dataPrevistaFim(request.dataPrevistaFim())
                .numeroContrato(request.numeroContrato())
                .endereco(request.endereco())
                .observacoes(request.observacoes())
                .tipoMaoDeObra(request.tipoMaoDeObra())
                .status(request.status())
                .ativo(true)
                .build();

        obraToSave.setTenantId(tenant.id());

        ObraEntity savedObra = writeObra.save(obraToSave);

        return CreateObraResponse.from(savedObra);
    }

    private void verifyIfPlanoAllowsCreateObra(Long tenantId) {
        Integer quantityObrasActive = loadObraPort.getQuantidadeObrasAtivasByTenantId(tenantId);

        PlanoDto plano = getPlanoAtivoByTenantIdService.execute(tenantId);

        if (plano.limiteObras() - quantityObrasActive <= 0) {
            throw new BadRequestException("Limite de obras atingido");
        }
    }
}
