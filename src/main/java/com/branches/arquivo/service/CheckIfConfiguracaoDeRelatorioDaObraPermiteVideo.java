package com.branches.arquivo.service;

import com.branches.exception.ForbiddenException;
import com.branches.obra.domain.ObraEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CheckIfConfiguracaoDeRelatorioDaObraPermiteVideo {

    public void execute(ObraEntity obra) {
        if (obra.getConfiguracaoRelatorios().getShowVideos()) return;

        throw new ForbiddenException();
    }
}
