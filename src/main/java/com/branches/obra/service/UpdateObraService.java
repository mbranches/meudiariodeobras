package com.branches.obra.service;

import com.branches.domain.GrupoDeObraEntity;
import com.branches.exception.ForbiddenException;
import com.branches.exception.NotFoundException;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.dto.request.UpdateObraRequest;
import com.branches.obra.repository.ObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateObraService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final ObraRepository obraRepository;
    private final GetGrupoDeObraByIdAndTenantIdService getGrupoDeObraByIdAndTenantIdService;

    public void execute(UpdateObraRequest request, String obraExternalId, String tenantExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity userTenant = getCurrentUserTenant(userTenants, tenantId);

        ObraEntity obra = obraRepository.findByIdExternoAndTenantId(obraExternalId, tenantId)
                .orElseThrow(() -> new NotFoundException("Obra não encontrada com idExterno: " + obraExternalId + " e tenantId: " + tenantId));

        checkIfUserCanUpdateObra(obra.getId(), userTenant);

        obra.setNome(request.nome());
        obra.setResponsavel(request.responsavel());
        obra.setContratante(request.contratante());
        obra.setTipoContrato(request.tipoContrato());
        obra.setDataInicio(request.dataInicio());
        obra.setDataPrevistaFim(request.dataPrevistaFim());
        obra.setNumeroContrato(request.numeroContrato());
        obra.setEndereco(request.endereco());
        obra.setObservacoes(request.observacoes());
        obra.setTipoMaoDeObra(request.tipoMaoDeObra());
        obra.setStatus(request.status());

        GrupoDeObraEntity grupo = getGrupoDeObraByIdAndTenantIdService.execute(request.grupoId(), tenantId);

        obra.setGrupo(grupo);

        obraRepository.save(obra);
    }

    private void checkIfUserCanUpdateObra(Long id, UserTenantEntity userTenant) {
        if (!userTenant.getAuthorities().getObras().getCanCreateAndEdit() || !userTenant.getObrasPermitidasIds().contains(id)) {
            throw new ForbiddenException();
        }
    }

    private UserTenantEntity getCurrentUserTenant(List<UserTenantEntity> userTenants, Long tenantId) {
        return userTenants.stream()
                .filter(ut -> ut.getTenantId().equals(tenantId))
                .findFirst()
                .orElseThrow(ForbiddenException::new);
    }
}
