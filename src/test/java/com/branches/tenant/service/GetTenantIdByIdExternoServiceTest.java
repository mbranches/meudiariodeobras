package com.branches.tenant.service;

import com.branches.exception.NotFoundException;
import com.branches.tenant.domain.TenantEntity;
import com.branches.tenant.repository.TenantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTenantIdByIdExternoServiceTest {
    @InjectMocks
    private GetTenantIdByIdExternoService service;
    @Mock
    private TenantRepository tenantRepository;

    private TenantEntity tenant;
    String idExterno = "external-id-123";

    @BeforeEach
    void setUp() {
        tenant = TenantEntity.builder()
                .id(1L)
                .idExterno(idExterno)
                .razaoSocial("Tenant Teste LTDA")
                .nomeFantasia("Tenant Teste")
                .cnpj("12.345.678/0001-90")
                .telefone("(11) 98765-4321")
                .logoUrl("http://example.com/logo.png")
                .ativo(true)
                .build();

    }

    @Test
    void deveRetornarComSucessoTenantQuandoIdExternoExistir() {
        when(tenantRepository.findTenantIdByIdExternoAndAtivoIsTrue(idExterno)).thenReturn(Optional.of(tenant.getId()));

        Long resultado = service.execute(idExterno);

        assertNotNull(resultado);
        assertEquals(tenant.getId(), resultado);
    }

    @Test
    void deveLancarNotFoundExceptionQuandoIdExternoNaoExistir() {
        when(tenantRepository.findTenantIdByIdExternoAndAtivoIsTrue(idExterno)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.execute(idExterno));

        String expectedMessage = "Tenant não encontrado com o idExterno: " + idExterno;

        assertEquals(expectedMessage, exception.getReason());
    }
}
