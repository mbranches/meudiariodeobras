package com.branches.tenant.service;

import com.branches.tenant.domain.TenantEntity;
import com.branches.shared.dto.TenantDto;
import com.branches.tenant.port.LoadTenantPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetTenantIdByIdExternoServiceTest {
    @InjectMocks
    private GetTenantIdByIdExternoService service;
    @Mock
    private LoadTenantPort loadTenant;

    private TenantDto tenantDto;
    private TenantEntity tenant;
    String idExterno = "external-id-123";

    @BeforeEach
    void setUp() {
        tenantDto = TenantDto.builder()
                .id(1L)
                .idExterno(idExterno)
                .razaoSocial("Tenant Teste LTDA")
                .nomeFantasia("Tenant Teste")
                .cnpj("12.345.678/0001-90")
                .telefone("(11) 98765-4321")
                .logoUrl("http://example.com/logo.png")
                .ativo(true)
                .build();
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
        when(loadTenant.getByIdExterno(idExterno)).thenReturn(tenant);

        TenantDto resultado = service.execute(idExterno);

        assertNotNull(resultado);
        assertEquals(tenantDto.id(), resultado.id());
        assertEquals(tenantDto.idExterno(), resultado.idExterno());
        assertEquals(tenantDto.razaoSocial(), resultado.razaoSocial());
        assertEquals(tenantDto.nomeFantasia(), resultado.nomeFantasia());
        assertEquals(tenantDto.cnpj(), resultado.cnpj());
        assertEquals(tenantDto.telefone(), resultado.telefone());
        assertEquals(tenantDto.logoUrl(), resultado.logoUrl());
        assertEquals(tenantDto.ativo(), resultado.ativo());
    }
}
