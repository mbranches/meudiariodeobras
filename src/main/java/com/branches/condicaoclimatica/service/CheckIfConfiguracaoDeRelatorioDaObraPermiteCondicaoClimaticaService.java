package com.branches.condicaoclimatica.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.ObraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfConfiguracaoDeRelatorioDaObraPermiteCondicaoClimaticaService {

    public void execute(ObraEntity obra) {
        if (obra.getConfiguracaoRelatorios().getShowCondicaoClimatica()) return;

        throw new ForbiddenException();
    }
}
