package com.branches.obra.dto.response;

import com.branches.obra.repository.projections.ObraResumeProjection;

public record ObraPermitidaResponse(
        String id,
        String nome
) {
    public static ObraPermitidaResponse from(ObraResumeProjection obra) {
        return new ObraPermitidaResponse(
                obra.getIdExterno(),
                obra.getNome()
        );
    }
}
