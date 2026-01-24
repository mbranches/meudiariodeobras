package com.branches.condicaoclimatica.service;

import com.branches.condicaoclimatica.dto.response.CondicaoClimaticaAnalisysResponse;
import com.branches.obra.domain.ObraEntity;
import com.branches.obra.service.GetObraByIdExternoAndTenantIdService;
import com.branches.relatorio.repository.RelatorioRepository;
import com.branches.relatorio.repository.projections.CondicaoClimaticaAnalysisProjection;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetCondicaoClimaticaAnalysisService {

    private final GetTenantIdByIdExternoService getTenantIdByIdExternoService;
    private final GetCurrentUserTenantService getCurrentUserTenantService;
    private final CheckIfUserCanViewCondicaoClimaticaService checkIfUserCanViewCondicaoClimaticaService;
    private final GetObraByIdExternoAndTenantIdService getObraByIdExternoAndTenantIdService;
    private final CheckIfConfiguracaoDeRelatorioDaObraPermiteCondicaoClimaticaService checkIfConfiguracaoDeRelatorioDaObraPermiteCondicaoClimaticaService;
    private final RelatorioRepository relatorioRepository;

    public CondicaoClimaticaAnalisysResponse execute(String tenantExternalId, String obraExternalId, List<UserTenantEntity> userTenants) {
        Long tenantId = getTenantIdByIdExternoService.execute(tenantExternalId);

        UserTenantEntity currentUserTenant = getCurrentUserTenantService.execute(userTenants, tenantId);

        checkIfUserCanViewCondicaoClimaticaService.execute(currentUserTenant);
        if (obraExternalId != null) {
            ObraEntity obra = getObraByIdExternoAndTenantIdService.execute(obraExternalId, tenantId);

            checkIfConfiguracaoDeRelatorioDaObraPermiteCondicaoClimaticaService.execute(obra);
        }

        CondicaoClimaticaAnalysisProjection analysis = relatorioRepository.findCondicaoClimaticaAnalysis(tenantId, obraExternalId);

        BigDecimal porcentagemPraticavel = getPorcentagem(analysis.getTotalPraticavel(), analysis.getTotal());
        BigDecimal porcentagemNaoPraticavel = getPorcentagem(analysis.getTotalImpraticavel(), analysis.getTotal());
        BigDecimal porcentagemChuva = getPorcentagem(analysis.getTotalChuvoso(), analysis.getTotal());
        BigDecimal porcentagemNublado = getPorcentagem(analysis.getTotalNublado(), analysis.getTotal());
        BigDecimal porcentagemClaro = getPorcentagem(analysis.getTotalClaro(), analysis.getTotal());


        return CondicaoClimaticaAnalisysResponse.from(
                analysis,
                porcentagemPraticavel,
                porcentagemNaoPraticavel,
                porcentagemChuva,
                porcentagemNublado,
                porcentagemClaro
        );
    }

    private BigDecimal getPorcentagem(Long parte, Long total) {
        if (total == 0) {
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(parte)
                .multiply(BigDecimal.valueOf(100))
                .divide(BigDecimal.valueOf(total), 2, RoundingMode.HALF_UP);

    }
}

