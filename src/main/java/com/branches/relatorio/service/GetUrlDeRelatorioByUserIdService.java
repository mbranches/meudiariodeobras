package com.branches.relatorio.service;

import com.branches.exception.NotFoundException;
import com.branches.relatorio.domain.ArquivoDeRelatorioDeUsuarioEntity;
import com.branches.relatorio.repository.ArquivoDeRelatorioDeUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetUrlDeRelatorioByUserIdService {
    private final ArquivoDeRelatorioDeUsuarioRepository arquivoDeRelatorioDeUsuarioRepository;

    public String execute(Long relatorioId, Long userId) {
        ArquivoDeRelatorioDeUsuarioEntity entity = arquivoDeRelatorioDeUsuarioRepository.findByUserIdAndRelatorioId(userId, relatorioId)
                .orElseThrow(() -> new NotFoundException("Arquivo de relatório não encontrado para o user"));

        return entity.getArquivoUrl();
    }
}
