package br.com.finance.authentication.impl;

import br.com.finance.authentication.domain.dto.RegisterUserDto;
import br.com.finance.authentication.domain.entities.User;
import br.com.finance.authentication.repositories.UserRepository;
import br.com.finance.authentication.services.EncoderService;
import br.com.finance.authentication.services.impl.AuthenticationServiceImpl;
import br.com.finance.authentication.validators.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserValidator validator;
    @Mock
    private EncoderService encoder;
    @Captor
    private ArgumentCaptor<User> captorUser;

    @Test
    void testRegister() {
        RegisterUserDto dto = new RegisterUserDto("janainamai", "janainamai@hotmail.com", "admin", "admin");

        service.register(dto);

        verify(validator).validateSamePassword(dto.password(), dto.confirmPassword());
        verify(validator).validateUsernameAlreadyExists(dto.login());
        verify(encoder).encode(dto.password());
        verify(repository).save(captorUser.capture());

        User savedUser = captorUser.getValue();
        assertThat(savedUser.getPassword()).isNotEqualTo(dto.password());
    }

}