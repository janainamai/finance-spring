package br.com.finance.authentication.impl;

import br.com.finance.authentication.domain.UserAccount;
import br.com.finance.authentication.dto.RegisterUserDto;
import br.com.finance.authentication.repository.UserRepository;
import br.com.finance.authentication.service.EncoderService;
import br.com.finance.authentication.service.impl.UserServiceImpl;
import br.com.finance.authentication.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository repository;
    @Mock
    private UserValidator validator;
    @Mock
    private EncoderService encoder;
    @Captor
    private ArgumentCaptor<UserAccount> captorUser;

    @Test
    void testRegister() {
        RegisterUserDto dto = new RegisterUserDto();
        dto.setLogin("janainamai");
        dto.setEmail("janaina@hotmail.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");
        dto.setPhone1("10 91234-5678");
        dto.setPhone1("99 98765-4321");

        service.register(dto);

        verify(validator).validateSamePassword(dto.getPassword(), dto.getConfirmPassword());
        verify(validator).validateUsernameAlreadyExists(dto.getLogin());
        verify(encoder).encode(dto.getPassword());
        verify(repository).save(captorUser.capture());

        UserAccount savedUser = captorUser.getValue();
        assertThat(savedUser.getPassword()).isNotEqualTo(dto.getPassword());
    }

}