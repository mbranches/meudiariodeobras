package com.branches.usertenant.dto.response;

import com.branches.assinatura.domain.AssinaturaEntity;

import java.time.LocalDate;

public record AssinaturaInfoResponse(
        PlanoInfoResponse plano,
        LocalDate dataInicio,
        LocalDate dataFim
) {
    public static AssinaturaInfoResponse from(AssinaturaEntity assinatura) {
        return new AssinaturaInfoResponse(
                PlanoInfoResponse.from(assinatura.getPlano()),
                assinatura.getDataInicio(),
                assinatura.getDataFim()
        );
    }
}
