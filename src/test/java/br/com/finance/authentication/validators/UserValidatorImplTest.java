package br.com.finance.authentication.validators;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.authentication.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorImplTest {

    @InjectMocks
    private UserValidatorImpl validator;
    @Mock
    private UserRepository userRepository;

    @Test
    void testValidateSamePassword() {
        assertThatCode(() -> validator.validateSamePassword("password1", "password1")).doesNotThrowAnyException();
    }

    @Test
    void testValidateSamePasswordWhenItsDifferent() {
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> validator.validateSamePassword("password1", "password2"))
                .withMessage("The passwords entered do not match");
    }

    @Test
    void testValidateUsernameAlreadyExists() {
        String username = "janainamai";

        when(userRepository.existsByLogin(username)).thenReturn(false);

        assertThatCode(() -> validator.validateUsernameAlreadyExists(username)).doesNotThrowAnyException();
    }

    @Test
    void testValidateUsernameAlreadyExistsWhenUsernameAlreadyExists() {
        String username = "janainamai";

        when(userRepository.existsByLogin(username)).thenReturn(true);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> validator.validateUsernameAlreadyExists(username))
                .withMessage("There is already a user with that username");
    }

}