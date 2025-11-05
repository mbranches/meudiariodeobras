package com.branches.obra.service;
import com.branches.obra.domain.ObraEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.branches.obra.domain.StatusObra;
import com.branches.obra.domain.TipoContratoDeObra;
import com.branches.obra.dto.request.CreateObraRequest;
import com.branches.obra.dto.response.CreateObraResponse;
import com.branches.obra.port.LoadObraPort;
import com.branches.obra.port.WriteObraPort;
import com.branches.plano.service.GetPlanoAtivoByTenantIdService;
import com.branches.shared.dto.PlanoDto;
import com.branches.shared.enums.TipoMaoDeObra;
import com.branches.shared.exception.BadRequestException;
import com.branches.shared.exception.ForbiddenException;
import com.branches.shared.dto.TenantDto;
import com.branches.tenant.service.GetTenantByIdExternoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateObraServiceTest {

    @Mock
    private WriteObraPort writeObraPort;

    @Mock
    private GetTenantByIdExternoService getTenantByIdExternoService;

    @InjectMocks
    private CreateObraService createObraService;

    @Mock
    private LoadObraPort loadObraPort;

    @Mock
    private GetPlanoAtivoByTenantIdService getPlanoAtivoByTenantIdService;

    private CreateObraRequest createObraRequest;
    private TenantDto tenantDto;
    private ObraEntity obraEntity;
    private String tenantExternalId;
    private List<Long> userTenantIds;
    private PlanoDto planoDto;

    @BeforeEach
    void setUp() {
        tenantExternalId = "tenant-ext-123";

        tenantDto = new TenantDto(
                1L,
                tenantExternalId,
                "Razão Social Teste",
                "Nome Fantasia Teste",
                "12345678000199",
                "http://logo.url",
                "11999999999",
                true
        );

        planoDto = new PlanoDto(
                1L,
                "Plano 1",
                "Descrição do Plano 1",
                BigDecimal.valueOf(212),
                50,
                50
        );

        createObraRequest = new CreateObraRequest(
                "Obra Teste",
                "João Silva",
                "Contratante Teste",
                TipoContratoDeObra.CONTRATADA,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 12, 31),
                "CONT-2025-001",
                "Rua Teste, 123",
                "Observações de teste",
                TipoMaoDeObra.PERSONALIZADA,
                StatusObra.EM_ANDAMENTO,
                null
        );

        obraEntity = ObraEntity.builder()
                .id(1L)
                .idExterno("obra-id-ext-123")
                .nome("Obra Teste")
                .responsavel("João Silva")
                .contratante("Contratante Teste")
                .tipoContrato(TipoContratoDeObra.CONTRATADA)
                .dataInicio(LocalDate.of(2025, 1, 1))
                .dataPrevistaFim(LocalDate.of(2025, 12, 31))
                .numeroContrato("CONT-2025-001")
                .endereco("Rua Teste, 123")
                .observacoes("Observações de teste")
                .capaUrl("http://capa.url")
                .tipoMaoDeObra(TipoMaoDeObra.PERSONALIZADA)
                .status(StatusObra.EM_ANDAMENTO)
                .ativo(true)
                .build();
        obraEntity.setTenantId(1L);
    }

    @Test
    void deveExecutarComSucessoQuandoTenantEstaNaLista() {
        userTenantIds = List.of(1L, 2L, 3L);

        when(getTenantByIdExternoService.execute(tenantExternalId)).thenReturn(tenantDto);
        when(writeObraPort.save(any(ObraEntity.class))).thenReturn(obraEntity);
        when(loadObraPort.getQuantidadeObrasAtivasByTenantId(tenantDto.id())).thenReturn(0);
        when(getPlanoAtivoByTenantIdService.execute(tenantDto.id())).thenReturn(planoDto);


        CreateObraResponse response = createObraService.execute(
                createObraRequest,
                tenantExternalId,
                userTenantIds
        );

        assertNotNull(response);
        assertEquals("obra-id-ext-123", response.id());
        assertEquals(createObraRequest.nome(), response.nome());
        assertEquals(createObraRequest.responsavel(), response.responsavel());
        assertEquals(createObraRequest.contratante(), response.contratante());
        assertEquals(createObraRequest.tipoContrato(), response.tipoContrato());
        assertEquals(createObraRequest.status(), response.status());
        assertEquals(createObraRequest.dataInicio(), response.dataInicio());
        assertEquals(createObraRequest.dataPrevistaFim(), response.dataPrevistaFim());
        assertEquals(createObraRequest.numeroContrato(), response.numeroContrato());
        assertEquals(createObraRequest.endereco(), response.endereco());
        assertEquals(createObraRequest.observacoes(), response.observacoes());
        assertEquals(createObraRequest.tipoMaoDeObra(), response.tipoMaoDeObra());
    }

    @Test
    void deveLancarForbiddenExceptionQuandoTenantNaoEstaNaLista() {
        userTenantIds = List.of(2L, 3L, 4L);

        when(getTenantByIdExternoService.execute(tenantExternalId)).thenReturn(tenantDto);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> createObraService.execute(
                        createObraRequest,
                        tenantExternalId,
                        userTenantIds
                )
        );

        assertNotNull(exception);
    }

    @Test
    void deveLancarBadRequestExceptionQuandoLimiteDeObrasAtingido() {
        userTenantIds = List.of(1L, 2L, 3L);

        when(getTenantByIdExternoService.execute(tenantExternalId)).thenReturn(tenantDto);
        when(loadObraPort.getQuantidadeObrasAtivasByTenantId(tenantDto.id())).thenReturn(50);
        when(getPlanoAtivoByTenantIdService.execute(tenantDto.id())).thenReturn(planoDto);

        BadRequestException exception = assertThrows(
                BadRequestException.class,
                () -> createObraService.execute(
                        createObraRequest,
                        tenantExternalId,
                        userTenantIds
                )
        );

        assertNotNull(exception);
        assertEquals("Limite de obras atingido", exception.getReason());
    }
}
