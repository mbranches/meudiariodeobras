package com.branches.relatorio.maodeobra.service;

import com.branches.exception.ForbiddenException;
import com.branches.relatorio.maodeobra.domain.GrupoMaoDeObraEntity;
import com.branches.relatorio.maodeobra.dto.request.UpdateGrupoMaoDeObraRequest;
import com.branches.relatorio.maodeobra.repository.GrupoMaoDeObraRepository;
import com.branches.tenant.service.GetTenantIdByIdExternoService;
import com.branches.user.domain.PermissionsCadastro;
import com.branches.user.domain.UserEntity;
import com.branches.usertenant.domain.UserTenantAuthorities;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.usertenant.service.GetCurrentUserTenantService;
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
class UpdateGrupoMaoDeObraServiceTest {

    @Mock
    private GetTenantIdByIdExternoService getTenantIdByIdExternoService;

    @Mock
    private GetCurrentUserTenantService getCurrentUserTenantService;

    @Mock
    private GetGrupoMaoDeObraByIdAndTenantIdService getGrupoMaoDeObraByIdAndTenantIdService;

    @Mock
    private GrupoMaoDeObraRepository grupoMaoDeObraRepository;

    @InjectMocks
    private UpdateGrupoMaoDeObraService updateGrupoMaoDeObraService;

    private String tenantExternalId;
    private Long tenantId;
    private Long grupoId;
    private UpdateGrupoMaoDeObraRequest request;
    private List<UserTenantEntity> userTenants;
    private UserTenantEntity userTenantWithAccess;
    private UserTenantEntity userTenantWithoutAccess;
    private GrupoMaoDeObraEntity grupoMaoDeObraEntity;

    @BeforeEach
    void setUp() {
        tenantExternalId = "tenant-ext-123";
        tenantId = 1L;
        grupoId = 1L;
        request = new UpdateGrupoMaoDeObraRequest("Grupo Atualizado");

        UserTenantAuthorities authoritiesWithAccess = UserTenantAuthorities.builder()
                .cadastros(PermissionsCadastro.builder()
                        .maoDeObra(true)
                        .build())
                .build();

        UserTenantAuthorities authoritiesWithoutAccess = UserTenantAuthorities.builder()
                .cadastros(PermissionsCadastro.builder()
                        .maoDeObra(false)
                        .build())
                .build();

        userTenantWithAccess = UserTenantEntity.builder()
                .user(UserEntity.builder().id(1L).build())
                .tenantId(tenantId)
                .authorities(authoritiesWithAccess)
                .build();

        userTenantWithoutAccess = UserTenantEntity.builder()
                .user(UserEntity.builder().id(1L).build())
                .tenantId(tenantId)
                .authorities(authoritiesWithoutAccess)
                .build();

        userTenants = List.of(userTenantWithAccess);

        grupoMaoDeObraEntity = GrupoMaoDeObraEntity.builder()
                .id(grupoId)
                .descricao("Grupo Original")
                .ativo(true)
                .build();
        grupoMaoDeObraEntity.setTenantId(tenantId);
    }

    @Test
    void deveExecutarComSucessoQuandoUsuarioTemPermissao() {
        when(getTenantIdByIdExternoService.execute(tenantExternalId)).thenReturn(tenantId);
        when(getCurrentUserTenantService.execute(userTenants, tenantId)).thenReturn(userTenantWithAccess);
        when(getGrupoMaoDeObraByIdAndTenantIdService.execute(grupoId, tenantId)).thenReturn(grupoMaoDeObraEntity);
        when(grupoMaoDeObraRepository.save(any(GrupoMaoDeObraEntity.class))).thenReturn(grupoMaoDeObraEntity);

        updateGrupoMaoDeObraService.execute(grupoId, tenantExternalId, request, userTenants);

        verify(grupoMaoDeObraRepository, times(1)).save(argThat(grupo ->
                grupo.getDescricao().equals("Grupo Atualizado") &&
                grupo.getId().equals(grupoId) &&
                grupo.isAtivo()
        ));
    }

    @Test
    void deveLancarForbiddenExceptionQuandoUsuarioNaoTemPermissao() {
        when(getTenantIdByIdExternoService.execute(tenantExternalId)).thenReturn(tenantId);
        when(getCurrentUserTenantService.execute(userTenants, tenantId)).thenReturn(userTenantWithoutAccess);

        ForbiddenException exception = assertThrows(
                ForbiddenException.class,
                () -> updateGrupoMaoDeObraService.execute(grupoId, tenantExternalId, request, userTenants)
        );

        assertNotNull(exception);
        verify(grupoMaoDeObraRepository, never()).save(any());
    }
}

