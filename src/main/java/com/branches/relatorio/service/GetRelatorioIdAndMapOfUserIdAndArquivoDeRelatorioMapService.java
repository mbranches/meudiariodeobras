package com.branches.relatorio.service;

import com.branches.relatorio.domain.ArquivoDeRelatorioDeUsuarioEntity;
import com.branches.relatorio.repository.ArquivoDeRelatorioDeUsuarioRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import com.branches.usertenant.domain.UserTenantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndMapOfUserIdAndArquivoDeRelatorioMapService {
    private final ArquivoDeRelatorioDeUsuarioRepository arquivoDeRelatorioDeUsuarioRepository;

    public Map<Long, Map<Long, ArquivoDeRelatorioDeUsuarioEntity>> execute(List<RelatorioDetailsProjection> relatorios, List<UserTenantEntity> userTenantsWithAccessToObra) {
        List<Long> relatorioIds = relatorios.stream().map(RelatorioDetailsProjection::getId).toList();
        List<Long> userIds = userTenantsWithAccessToObra.stream().map(ut -> ut.getUser().getId()).toList();

        List<ArquivoDeRelatorioDeUsuarioEntity> arquivos = arquivoDeRelatorioDeUsuarioRepository.findAllByRelatorioIdInAndUserIdIn(relatorioIds, userIds);

        return relatorioIds.stream()
                .collect(Collectors.toMap(
                                relatorioId -> relatorioId,
                                relatorioId -> arquivos.stream()
                                        .filter(a -> a.getRelatorioId().equals(relatorioId))
                                        .collect(Collectors.toMap(ArquivoDeRelatorioDeUsuarioEntity::getUserId, a -> a))
                        )
                );
    }
}
