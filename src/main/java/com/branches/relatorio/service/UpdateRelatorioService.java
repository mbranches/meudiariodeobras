package com.branches.relatorio.service;

import com.branches.exception.ForbiddenException;
import com.branches.exception.NotFoundException;
import com.branches.obra.domain.ConfiguracaoRelatoriosEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.repository.ObraRepository;
import com.branches.relatorio.domain.RelatorioEntity;
import com.branches.relatorio.dto.request.UpdateRelatorioRequest;
import com.branches.relatorio.domain.enums.StatusRelatorio;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import com.branches.utils.CalculateHorasTotais;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UpdateRelatorioService {
    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final GetRelatorioByIdExternoAndTenantIdService getRelatorioByIdExternoAndTenantIdService;
    private final RelatorioRepository relatorioRepository;
    private final ObraRepository obraRepository;
    private final CalculateHorasTotais calculateHorasTotais;

    @Transactional
    public void execute(UpdateRelatorioRequest request, String tenantExternalId, String relatorioExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserCanUpdateRelatorio(currentUserTenant);

        RelatorioEntity relatorio = getRelatorioByIdExternoAndTenantIdService.execute(relatorioExternalId, tenantId);

        ObraEntity obra = obraRepository.findById(relatorio.getObraId())
                        .orElseThrow(() -> new NotFoundException("Não foi possível encontra a obra do relatório com id: " + relatorioExternalId));

        ConfiguracaoRelatoriosEntity configuracaoRelatorios = obra.getConfiguracaoRelatorios();

        relatorio.setNumero(request.numeroRelatorio());
        relatorio.setDataInicio(request.dataInicio());
        relatorio.setDataFim(request.dataFim());
        relatorio.setPrazoContratualObra(request.prazoContratual());
        relatorio.setPrazoDecorridoObra(request.prazoDecorrido());
        relatorio.setPrazoPraVencerObra(request.prazoPraVencer());
        updateStatus(currentUserTenant, relatorio, request.status());

        if (configuracaoRelatorios.getShowHorarioDeTrabalho()) {
            relatorio.setHoraInicioTrabalhos(request.horaInicioTrabalhos());
            relatorio.setHoraFimTrabalhos(request.horaFimTrabalhos());
            relatorio.setHorasIntervalo(request.horasIntervalo());
            relatorio.setHorasTrabalhadas(calculateHorasTotais.execute(request.horaInicioTrabalhos(), request.horaFimTrabalhos(), request.horasIntervalo()));
        }

        relatorioRepository.save(relatorio);

        //todo: gerar html do relatorio
        //todo: substituir relatorio pdf antigo
        //todo: subir no s3
    }

    private void updateStatus(UserTenantEntity currentUserTenant, RelatorioEntity relatorio, StatusRelatorio status) {
        if (status == StatusRelatorio.APROVADO && !currentUserTenant.getAuthorities().getRelatorios().getCanAprovar()) return;

        relatorio.setStatus(status);
    }

    private void checkIfUserCanUpdateRelatorio(UserTenantEntity currentUserTenant) {
        if (currentUserTenant.getAuthorities().getRelatorios().getCanCreateAndEdit()) return;

        throw new ForbiddenException();
    }
}