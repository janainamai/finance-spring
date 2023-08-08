package br.com.finance.finance.services;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.components.CreditTransactionImpl;
import br.com.finance.finance.components.DebitTransactionImpl;
import br.com.finance.finance.components.factory.TransactionComponentFactory;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.dto.TransactionDto;
import br.com.finance.finance.services.interfaces.BankAccountService;
import br.com.finance.finance.utils.FinanceConstants;
import lombok.Getter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl service;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionComponentFactory transactionComponentFactory;
    @Mock
    private DebitTransactionImpl debitTransaction;
    @Mock
    private CreditTransactionImpl creditTransaction;
    @Mock
    private BankAccountService bankAccountService;
    @Getter
    @Captor
    private ArgumentCaptor<TransactionEntity> captorTransaction;

    @Test
    @DisplayName("Should return the found transaction")
    void testGetByIdWhenFound() {
        UUID transactionId = UUID.randomUUID();

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(UUID.randomUUID());

        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setId(transactionId);
        transactionEntity.setDescription("Description transaction");
        transactionEntity.setAmount(BigDecimal.TEN);
        transactionEntity.setTransactionType(EnumTransactionType.CREDIT);
        transactionEntity.setEventDate(LocalDate.now());
        transactionEntity.setEventTime(LocalTime.now());
        transactionEntity.setBankAccount(bankAccountEntity);
        when(transactionRepository.findById(transactionId)).thenReturn(Optional.of(transactionEntity));

        TransactionDto dto = service.getById(transactionId.toString());

        assertThat(dto.getId()).isEqualTo(transactionEntity.getId().toString());
        assertThat(dto.getDescription()).isEqualTo(transactionEntity.getDescription());
        assertThat(dto.getAmount()).isEqualTo(transactionEntity.getAmount());
        assertThat(dto.getTransactionType()).isEqualTo(transactionEntity.getTransactionType());
        assertThat(dto.getEventDate()).isEqualTo(transactionEntity.getEventDate());
        assertThat(dto.getEventTime()).isEqualTo(transactionEntity.getEventTime());
        assertThat(dto.getBankAccountId()).isEqualTo(transactionEntity.getBankAccount().getId().toString());
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a transaction with the given ID")
    void testGetByIdWhenNotFound() {
        UUID transactionId = UUID.randomUUID();

        when(transactionRepository.findById(transactionId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.getById(transactionId.toString()))
                .withMessage("Transaction not found with id ".concat(transactionId.toString()));
    }

    @DisplayName("Should save the transaction and update value in bank account")
    @Test
    void testCreateWhenDebit() {
        TransactionDto dto = new TransactionDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setDescription("Transaction description");
        dto.setTransactionType(EnumTransactionType.DEBIT);
        dto.setAmount(BigDecimal.TEN);
        dto.setEventDate(LocalDate.now());
        dto.setEventTime(LocalTime.now());
        dto.setBankAccountId(UUID.randomUUID().toString());

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(UUID.fromString(dto.getBankAccountId()));
        when(bankAccountService.getEntityById(dto.getBankAccountId())).thenReturn(bankAccountEntity);

        TransactionEntity savedTransaction = new TransactionEntity();
        savedTransaction.setAmount(dto.getAmount().setScale(FinanceConstants.INTEGER_TWO, RoundingMode.HALF_UP));
        when(transactionRepository.save(any())).thenReturn(savedTransaction);

        when(transactionComponentFactory.getStrategy(dto.getTransactionType())).thenReturn(debitTransaction);

        service.create(dto);

        verify(transactionRepository).save(captorTransaction.capture());
        TransactionEntity transactionEntity = captorTransaction.getValue();
        assertThat(transactionEntity.getDescription()).isEqualTo(dto.getDescription());
        assertThat(transactionEntity.getTransactionType()).isEqualTo(dto.getTransactionType());
        assertThat(transactionEntity.getAmount()).isEqualTo(dto.getAmount().setScale(FinanceConstants.INTEGER_TWO, RoundingMode.HALF_UP));
        assertThat(transactionEntity.getEventDate()).isEqualTo(dto.getEventDate());
        assertThat(transactionEntity.getEventTime()).isEqualTo(dto.getEventTime());
        assertThat(transactionEntity.getBankAccount().getId()).hasToString(dto.getBankAccountId());

        verify(debitTransaction).updateTransactionValueInBankAccount(savedTransaction.getAmount(), dto.getBankAccountId());
        verify(creditTransaction, times(0)).updateTransactionValueInBankAccount(savedTransaction.getAmount(), dto.getBankAccountId());
    }

    @DisplayName("Should save the transaction and update value in bank account")
    @Test
    void testCreateWhenCredit() {
        TransactionDto dto = new TransactionDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setDescription("Transaction description");
        dto.setTransactionType(EnumTransactionType.CREDIT);
        dto.setAmount(BigDecimal.TEN);
        dto.setEventDate(LocalDate.now());
        dto.setEventTime(LocalTime.now());
        dto.setBankAccountId(UUID.randomUUID().toString());

        BankAccountEntity bankAccountEntity = new BankAccountEntity();
        bankAccountEntity.setId(UUID.fromString(dto.getBankAccountId()));
        when(bankAccountService.getEntityById(dto.getBankAccountId())).thenReturn(bankAccountEntity);

        TransactionEntity savedTransaction = new TransactionEntity();
        savedTransaction.setAmount(dto.getAmount().setScale(FinanceConstants.INTEGER_TWO, RoundingMode.HALF_UP));
        when(transactionRepository.save(any())).thenReturn(savedTransaction);

        when(transactionComponentFactory.getStrategy(dto.getTransactionType())).thenReturn(creditTransaction);

        service.create(dto);

        verify(transactionRepository).save(captorTransaction.capture());
        TransactionEntity transactionEntity = captorTransaction.getValue();
        assertThat(transactionEntity.getDescription()).isEqualTo(dto.getDescription());
        assertThat(transactionEntity.getTransactionType()).isEqualTo(dto.getTransactionType());
        assertThat(transactionEntity.getAmount()).isEqualTo(dto.getAmount().setScale(FinanceConstants.INTEGER_TWO, RoundingMode.HALF_UP));
        assertThat(transactionEntity.getEventDate()).isEqualTo(dto.getEventDate());
        assertThat(transactionEntity.getEventTime()).isEqualTo(dto.getEventTime());
        assertThat(transactionEntity.getBankAccount().getId()).hasToString(dto.getBankAccountId());

        verify(creditTransaction).updateTransactionValueInBankAccount(savedTransaction.getAmount(), dto.getBankAccountId());
        verify(debitTransaction, times(0)).updateTransactionValueInBankAccount(savedTransaction.getAmount(), dto.getBankAccountId());
    }

}