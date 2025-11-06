package com.branches.user.service;

import com.branches.exception.NotFoundException;
import com.branches.user.domain.*;
import com.branches.user.domain.enums.Role;
import com.branches.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetUserByEmailServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetUserByEmailService getUserByEmailService;

    private UserEntity userEntity;
    private String email;

    @BeforeEach
    void setUp() {
        email = "teste@example.com";

        userEntity = UserEntity.builder()
                .id(1L)
                .idExterno("user-ext-123")
                .nome("João Silva")
                .email(email)
                .password("senhaEncriptada123")
                .cargo("Engenheiro")
                .role(Role.USER)
                .fotoUrl("http://foto.url/joao.jpg")
                .ativo(true)
                .build();

        UserTenantEntity userTenantEntity = new UserTenantEntity();
        userTenantEntity.setUser(userEntity);
        userTenantEntity.setTenantId(1L);
        userTenantEntity.setarId();
        userTenantEntity.setUserObraPermitidaEntities(Set.of(new UserObraPermitidaEntity(UserObraPermitidaKey.from(userTenantEntity, 1L), userTenantEntity, 1L)));

        userEntity.setUserTenantEntities(List.of(userTenantEntity));
    }

    @Test
    void deveRetornarUserDtoQuandoUsuarioEncontrado() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));

        UserEntity resultado = getUserByEmailService.execute(email);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("user-ext-123", resultado.getIdExterno());
        assertEquals("João Silva", resultado.getNome());
        assertEquals(email, resultado.getEmail());
        assertEquals("senhaEncriptada123", resultado.getPassword());
        assertEquals("Engenheiro", resultado.getCargo());
        assertEquals(Role.USER, resultado.getRole());
        assertEquals("http://foto.url/joao.jpg", resultado.getFotoUrl());
        assertTrue(resultado.getAtivo());
        assertEquals(List.of(1L), resultado.getTenantsIds());
    }

    @Test
    void deveLancarNotFoundExceptionQuandoUsuarioNaoEncontrado() {
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> getUserByEmailService.execute(email));

        String expectedMessage = "User não encontrado com email: " + email;

        assertEquals(expectedMessage, exception.getReason());
    }
}