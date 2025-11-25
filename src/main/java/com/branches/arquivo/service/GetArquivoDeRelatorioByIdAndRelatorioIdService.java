package com.branches.arquivo.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.arquivo.domain.enums.TipoArquivo;
import com.branches.arquivo.repository.ArquivoRepository;
import com.branches.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetArquivoDeRelatorioByIdAndRelatorioIdService {
    private final ArquivoRepository arquivoRepository;

    public ArquivoEntity execute(Long arquivoId, Long relatorioId, TipoArquivo tipoArquivo) {
        return arquivoRepository.findByIdAndRelatorioIdAndTipoArquivo(arquivoId, relatorioId, tipoArquivo)
                .orElseThrow(() -> new NotFoundException("Arquivo não encontrado com o id: " + arquivoId));
    }
}
