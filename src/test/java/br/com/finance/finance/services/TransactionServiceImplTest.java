package br.com.finance.finance.services;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.components.interfaces.TransactionComponent;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.dto.TransactionDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl service;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionComponent transactionComponent;

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

    @Test
    @DisplayName("Should save the transaction and update value in bank account")
    void testCreate() {
        TransactionDto dto = new TransactionDto();
        dto.setId(UUID.randomUUID().toString());
        dto.setBankAccountId(UUID.randomUUID().toString());

        TransactionEntity transactionEntity = new TransactionEntity();
        when(transactionComponent.saveTransaction(dto)).thenReturn(transactionEntity);

        service.create(dto);

        verify(transactionComponent).saveTransaction(dto);
        verify(transactionComponent).updateTransactionValueInBankAccount(transactionEntity, dto.getBankAccountId());
    }

}