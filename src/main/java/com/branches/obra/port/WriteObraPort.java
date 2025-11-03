package com.branches.obra.port;

import com.branches.obra.domain.ObraEntity;

public interface WriteObraPort {
    ObraEntity save(ObraEntity obraEntity);
}
