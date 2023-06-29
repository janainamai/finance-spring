package br.com.planner.authentication.service.impl;

import br.com.planner.authentication.domain.UserAccount;
import br.com.planner.authentication.dto.RegisterUserDto;
import br.com.planner.authentication.repository.UserRepository;
import br.com.planner.authentication.service.EncoderService;
import br.com.planner.authentication.validator.UserValidator;
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
        dto.setFirstName("Janaina");
        dto.setFirstName("Mai");
        dto.setUsername("janainamai");
        dto.setEmail("janaina@hotmail.com");
        dto.setPassword("password123");
        dto.setConfirmPassword("password123");
        dto.setBirthdate(LocalDate.of(2000, 1, 1));
        dto.setPhone1("10 91234-5678");
        dto.setPhone1("99 98765-4321");

        service.register(dto);

        verify(validator).validateSamePassword(dto.getPassword(), dto.getConfirmPassword());
        verify(validator).validateUsernameAlreadyExists(dto.getUsername());
        verify(encoder).encode(dto.getPassword());
        verify(repository).save(captorUser.capture());

        UserAccount savedUser = captorUser.getValue();
        assertThat(savedUser.getPassword()).isNotEqualTo(dto.getPassword());
    }

    @Test
    void testGetByUsernameWhenUserIsFound() {
        String username = "janainamai";

        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName("Janaina");
        userAccount.setFirstName("Mai");
        userAccount.setUsername(username);
        userAccount.setEmail("janaina@hotmail.com");
        userAccount.setPassword("password123");
        userAccount.setBirthdate(LocalDate.of(2000, 1, 1));
        userAccount.setPhone1("10 91234-5678");
        userAccount.setPhone1("99 98765-4321");
        when(repository.findByUsername(username)).thenReturn(Optional.of(userAccount));

        Optional<UserAccount> optional = service.getByUsername(username);
        assertThat(optional).isNotEmpty();
        assertThat(optional.get()).isEqualTo(userAccount);
    }

}