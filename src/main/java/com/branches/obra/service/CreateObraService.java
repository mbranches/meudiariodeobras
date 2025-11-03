package com.branches.obra.service;

import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.request.CreateObraRequest;
import com.branches.obra.dto.response.CreateObraResponse;
import com.branches.obra.port.WriteObraPort;
import com.branches.shared.exception.ForbiddenException;
import com.branches.tenant.dto.TenantDto;
import com.branches.tenant.service.GetTenantByIdExternoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CreateObraService {
    private final WriteObraPort writeObra;
    private final GetTenantByIdExternoService getTenantByIdExternoService;

    public CreateObraResponse execute(CreateObraRequest request, String tenantDaObraExternalId, List<Long> requestingUserTenantIds) {
        TenantDto tenant = getTenantByIdExternoService.execute(tenantDaObraExternalId);

        if (!requestingUserTenantIds.contains(tenant.id())) {
            throw new ForbiddenException();
        }

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
                .capaUrl(request.capaUrl())
                .tipoMaoDeObra(request.tipoMaoDeObra())
                .status(request.status())
                .ativo(true)
                .build();

        obraToSave.setTenantId(tenant.id());

        ObraEntity savedObra = writeObra.save(obraToSave);

        return CreateObraResponse.from(savedObra);
    }
}
