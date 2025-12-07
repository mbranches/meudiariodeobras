package com.branches.relatorio.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.arquivo.repository.ArquivoRepository;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetRelatorioIdAndArquivosMapService {
    private final ArquivoRepository arquivoRepository;

    public Map<Long, List<ArquivoEntity>> execute(List<RelatorioDetailsProjection> relatorios) {
        List<Long> relatoriosIdsToQuery = relatorios.stream()
                //todo: quando adicionar anexo, adicionar aqui também
                .filter(r -> r.getShowFotos() || r.getShowVideos()) //todo: quando for pegar a lista de fotos ou videos, fazer a checagem individualmente
                .map(RelatorioDetailsProjection::getId)
                .toList();
        List<ArquivoEntity> arquivos = arquivoRepository.findAllByRelatorioIdIn(relatoriosIdsToQuery);

        Map<Long, List<ArquivoEntity>> map = relatorios.stream()
                .map(RelatorioDetailsProjection::getId)
                .collect(Collectors.toMap(id -> id, id -> new ArrayList<>()));

        arquivos.forEach(arquivo ->
                map.get(arquivo.getRelatorio().getId()).add(arquivo)
        );

        return map;
    }
}
