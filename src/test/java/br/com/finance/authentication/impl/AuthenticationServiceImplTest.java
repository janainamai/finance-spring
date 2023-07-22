package br.com.finance.authentication.impl;

import br.com.finance.authentication.domain.entities.RoleEntity;
import br.com.finance.authentication.domain.entities.UserEntity;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.EncoderService;
import br.com.finance.authentication.services.UserRoleService;
import br.com.finance.authentication.services.dto.RegisterUserDto;
import br.com.finance.authentication.services.impl.AuthenticationServiceImpl;
import br.com.finance.authentication.utils.RoleConstants;
import br.com.finance.authentication.validators.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRoleService roleService;
    @Mock
    private UserValidator validator;
    @Mock
    private EncoderService encoder;
    @Captor
    private ArgumentCaptor<UserEntity> captorUser;

    @BeforeEach
    void mockFreeUserRole() {
        when(roleService.getRoleByName(RoleConstants.FREE_USER)).thenReturn(new RoleEntity());
    }

    @Test
    void testRegisterSavesEncryptedPassword() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janainamai@hotmail.com");
        dto.setPassword("admin");
        dto.setConfirmPassword("admin");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        service.register(dto);

        verify(encoder).encode(dto.getPassword());
        verify(userRepository).save(captorUser.capture());

        UserEntity savedUser = captorUser.getValue();
        assertThat(savedUser.getPassword()).isNotEqualTo(dto.getPassword());
    }

    @Test
    void testRegisterValidatesSamePassword() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janainamai@hotmail.com");
        dto.setPassword("admin");
        dto.setConfirmPassword("admin");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        service.register(dto);

        verify(validator).validateSamePassword(dto.getPassword(), dto.getConfirmPassword());
    }

    @Test
    void testRegisterValidatesUsarnameAlreadyExists() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janainamai@hotmail.com");
        dto.setPassword("admin");
        dto.setConfirmPassword("admin");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        service.register(dto);

        verify(validator).validateUsernameAlreadyExists(dto.getLogin());
    }

    @Test
    void testRegisterWhenIsNotFirstUser() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janainamai@hotmail.com");
        dto.setPassword("admin");
        dto.setConfirmPassword("admin");

        when(userRepository.findAll()).thenReturn(List.of(new UserEntity()));

        service.register(dto);

        verify(roleService).getRoleByName(RoleConstants.FREE_USER);
        verify(roleService, times(0)).getRoleByName(RoleConstants.ADMIN);
    }

    @Test
    void testRegisterWhenIsFirstUser() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janainamai@hotmail.com");
        dto.setPassword("admin");
        dto.setConfirmPassword("admin");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        when(roleService.getRoleByName(RoleConstants.ADMIN)).thenReturn(new RoleEntity());

        service.register(dto);

        verify(roleService).getRoleByName(RoleConstants.FREE_USER);
        verify(roleService).getRoleByName(RoleConstants.ADMIN);
    }

}