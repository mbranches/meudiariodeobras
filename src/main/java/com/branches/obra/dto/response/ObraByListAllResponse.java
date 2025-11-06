package com.branches.obra.dto.response;

import com.branches.obra.domain.ObraEntity;
import com.branches.obra.domain.StatusObra;

public record ObraByListAllResponse(
        String id,
        String nome,
        StatusObra status,
        String capaUrl
) {
    public static ObraByListAllResponse from(ObraEntity obra) {
        return new ObraByListAllResponse(
                obra.getIdExterno(),
                obra.getNome(),
                obra.getStatus(),
                obra.getCapaUrl()
        );
    }
}
