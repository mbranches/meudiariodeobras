package com.branches.obra.service;
import com.branches.assinatura.domain.AssinaturaEntity;
import com.branches.assinatura.domain.enums.AssinaturaStatus;
import com.branches.assinatura.service.GetAssinaturaActiveByTenantIdService;
import com.branches.obra.domain.ObraEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.branches.obra.domain.StatusObra;
import com.branches.obra.domain.TipoContratoDeObra;
import com.branches.obra.dto.request.CreateObraRequest;
import com.branches.obra.dto.response.CreateObraResponse;
import com.branches.obra.domain.enums.TipoMaoDeObra;
import com.branches.exception.BadRequestException;
import com.branches.exception.ForbiddenException;
import com.branches.obra.repository.ObraRepository;
import com.branches.plano.domain.PlanoEntity;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateObraServiceTest {

    @Mock
    private ObraRepository obraRepository;

    @Mock
    private GetTenantIdByIdExternoService getTenantIdByIdExternoService;

    @InjectMocks
    private CreateObraService createObraService;

    @Mock
    private GetAssinaturaActiveByTenantIdService getAssinaturaActiveByTenantIdService;

    private CreateObraRequest createObraRequest;
    private ObraEntity savedObra;
    private ObraEntity obraToSave;
    private String tenantExternalId;
    private Long tenantId;
    private List<Long> userTenantIds;
    private PlanoEntity plano;
    private AssinaturaEntity assinatura;

    @BeforeEach
    void setUp() {
        tenantExternalId = "tenant-ext-123";
        tenantId = 1L;

        plano = new PlanoEntity(
                1L,
                "Plano 1",
                "Descrição do Plano 1",
                BigDecimal.valueOf(212),
                50,
                50,
                50
        );

        assinatura = AssinaturaEntity.builder()
                .id(1L)
                .tenantId(tenantId)
                .plano(plano)
                .dataInicio(LocalDate.of(2025, 1, 1))
                .dataFim(LocalDate.of(2025, 12, 31))
                .status(AssinaturaStatus.ATIVO)
                .build();

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

        savedObra = ObraEntity.builder()
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
        savedObra.setTenantId(1L);

        obraToSave = ObraEntity.builder()
                .nome("Obra Teste")
                .responsavel("João Silva")
                .contratante("Contratante Teste")
                .tipoContrato(TipoContratoDeObra.CONTRATADA)
                .dataInicio(LocalDate.of(2025, 1, 1))
                .dataPrevistaFim(LocalDate.of(2025, 12, 31))
                .numeroContrato("CONT-2025-001")
                .endereco("Rua Teste, 123")
                .observacoes("Observações de teste")
                .tipoMaoDeObra(TipoMaoDeObra.PERSONALIZADA)
                .status(StatusObra.EM_ANDAMENTO)
                .ativo(true)
                .build();
        obraToSave.setTenantId(1L);
    }

    @Test
    void deveExecutarComSucessoQuandoTenantEstaNaLista() {
        userTenantIds = List.of(1L, 2L, 3L);

        when(getTenantIdByIdExternoService.execute(tenantExternalId)).thenReturn(tenantId);
        when(obraRepository.save(obraToSave)).thenReturn(savedObra);
        when(obraRepository.countByTenantIdAndAtivoIsTrue(tenantId)).thenReturn(0);
        when(getAssinaturaActiveByTenantIdService.execute(tenantId)).thenReturn(assinatura);


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

        when(getTenantIdByIdExternoService.execute(tenantExternalId)).thenReturn(tenantId);

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

        when(getTenantIdByIdExternoService.execute(tenantExternalId)).thenReturn(tenantId);
        when(obraRepository.countByTenantIdAndAtivoIsTrue(tenantId)).thenReturn(50);
        when(getAssinaturaActiveByTenantIdService.execute(tenantId)).thenReturn(assinatura);

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
