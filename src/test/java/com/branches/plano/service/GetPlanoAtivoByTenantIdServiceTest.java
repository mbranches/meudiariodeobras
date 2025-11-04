package com.branches.plano.service;

import com.branches.assinatura.service.GetAssinaturaActiveByTenantIdService;
import com.branches.plano.domain.PlanoEntity;
import com.branches.plano.port.LoadPlanoPort;
import com.branches.shared.dto.AssinaturaDto;
import com.branches.shared.dto.PlanoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPlanoAtivoByTenantIdServiceTest {
    @InjectMocks
    private GetPlanoAtivoByTenantIdService service;

    @Mock
    private LoadPlanoPort loadPlanoPort;

    @Mock
    private GetAssinaturaActiveByTenantIdService getAssinaturaActiveByTenantIdService;

    private Long tenantId;
    private Long planoId;
    private AssinaturaDto assinaturaDto;
    private PlanoEntity planoEntity;

    @BeforeEach
    void setup() {
        tenantId = 1L;
        planoId = 2L;

        assinaturaDto = new AssinaturaDto(
                1L,
                tenantId,
                planoId
        );

        planoEntity = PlanoEntity.builder()
                .id(planoId)
                .nome("Plano Premium")
                .descricao("Plano premium com recursos completos")
                .preco(new BigDecimal("99.90"))
                .duracaoDias(30)
                .limiteUsuarios(10)
                .limiteObras(50)
                .build();
    }

    @Test
    void deveExecutarGetPlanoAtivoByTenantIdComSucesso() {
        when(getAssinaturaActiveByTenantIdService.execute(tenantId))
                .thenReturn(assinaturaDto);
        when(loadPlanoPort.getPlanoById(planoId))
                .thenReturn(planoEntity);

        PlanoDto response = service.execute(tenantId);

        assertNotNull(response);
        assertEquals(planoEntity.getId(), response.id());
        assertEquals(planoEntity.getNome(), response.nome());
        assertEquals(planoEntity.getDescricao(), response.descricao());
        assertEquals(planoEntity.getPreco(), response.preco());
        assertEquals(planoEntity.getLimiteUsuarios(), response.limiteUsuarios());
        assertEquals(planoEntity.getLimiteObras(), response.limiteObras());
    }
}