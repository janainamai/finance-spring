package br.com.finance.finance.validators;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.repositories.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountValidatorImplTest {

    @InjectMocks
    private BankAccountValidatorImpl validator;
    @Mock
    private BankAccountRepository repository;

    @Test
    void testValidateNameExistsDoesNotThrowAnException() {
        String name = "Mercado Pago";

        when(repository.existsByName(name)).thenReturn(false);

        assertDoesNotThrow(() -> validator.validateNameExists(name));
    }

    @Test
    void testValidateNameExistsThrowAnException() {
        String name = "Mercado Pago";

        when(repository.existsByName(name)).thenReturn(true);

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> validator.validateNameExists(name))
                .withMessage("Already exists a bank account with this name");
    }

}