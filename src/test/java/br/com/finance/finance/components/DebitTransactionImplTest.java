package br.com.finance.finance.components;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.interfaces.BankAccountService;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.math.BigDecimal;
import java.util.UUID;

import static br.com.finance.finance.utils.FinanceConstants.INTEGER_TWO;
import static java.math.RoundingMode.HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebitTransactionImplTest {

    @InjectMocks
    private DebitTransactionImpl debitTransaction;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private BankAccountRepository bankAccountRepository;
    @Captor
    private ArgumentCaptor<BankAccountEntity> captorBankAccount;

    @Test
    @DisplayName("Should return strategy name")
    void testGetStrategyName() {
        EnumTransactionType transactionType = debitTransaction.getStrategyName();

        assertThat(transactionType).isEqualTo(EnumTransactionType.DEBIT);
    }

    @Test
    @DisplayName("Should subtract the transaction amount of the total balance of the bank account")
    void testUpdateTransactionValueInBankAccountWhenSucess() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setTotalBalance(BigDecimal.valueOf(500));
        when(bankAccountService.getEntityById(bankAccountId.toString())).thenReturn(bankAccountEntity);

        debitTransaction.updateTransactionValueInBankAccount(BigDecimal.TEN, bankAccountId.toString());

        verify(bankAccountRepository).save(captorBankAccount.capture());

        BankAccountEntity bankAccount = captorBankAccount.getValue();
        assertThat(bankAccount.getTotalBalance()).isEqualTo(BigDecimal.valueOf(490).setScale(INTEGER_TWO, HALF_UP));
    }

    @Test
    @DisplayName("Should throw exception when save method throws lock exception five times")
    void testUpdateTransactionValueInBankAccountWhenError() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setTotalBalance(BigDecimal.valueOf(500));
        when(bankAccountService.getEntityById(bankAccountId.toString())).thenReturn(bankAccountEntity);

        when(bankAccountRepository.save(any())).thenThrow(ObjectOptimisticLockingFailureException.class);

        assertThatExceptionOfType(ServiceException.class)
                .isThrownBy(() -> debitTransaction.updateTransactionValueInBankAccount(BigDecimal.TEN, bankAccountId.toString()))
                .withMessage("Unable to update transaction amount in bank account, please try again");
    }

}