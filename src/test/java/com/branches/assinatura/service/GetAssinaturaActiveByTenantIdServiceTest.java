package com.branches.assinatura.service;

import com.branches.assinatura.domain.AssinaturaEntity;
import com.branches.assinatura.domain.enums.AssinaturaStatus;
import com.branches.assinatura.port.LoadAssinaturaPort;
import com.branches.shared.dto.AssinaturaDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAssinaturaActiveByTenantIdServiceTest {
    @InjectMocks
    private GetAssinaturaActiveByTenantIdService service;
    @Mock
    private LoadAssinaturaPort loadAssinaturaPort;

    private Long tenantId;
    private AssinaturaEntity assinatura;

    @BeforeEach
    void setup() {
        assinatura = AssinaturaEntity.builder()
                .id(1L)
                .tenantId(1L)
                .planoId(2L)
                .status(AssinaturaStatus.ATIVO)
                .dataInicio(LocalDate.of(2025, 1, 1))
                .dataFim(LocalDate.of(2026, 1, 1))
                .build();
        tenantId = 1L;
    }

    @Test
    void deveExecutarGetAssinaturaByTenantIdComSucesso() {
        when(loadAssinaturaPort.getAssinaturaAtivaByTenantId(tenantId))
            .thenReturn(assinatura);

        AssinaturaDto response = service.execute(tenantId);

        assertNotNull(response);
        assertEquals(assinatura.getId(), response.id());
        assertEquals(assinatura.getTenantId(), response.tenantId());
        assertEquals(assinatura.getPlanoId(), response.planoId());
    }
}