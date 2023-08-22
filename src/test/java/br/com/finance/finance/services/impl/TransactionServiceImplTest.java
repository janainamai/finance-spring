package br.com.finance.finance.services.impl;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.TransactionServiceImpl;
import br.com.finance.finance.services.dto.FilterTransactionDto;
import br.com.finance.finance.services.dto.TransactionDto;
import br.com.finance.finance.utils.FinanceUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
    private TransactionRepository repository;

    @Test
    @DisplayName("Should return the found transaction")
    void testGetByIdWhenFound() {
        UUID transactionId = UUID.randomUUID();

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(UUID.randomUUID());
        bankAccount.setName("Name of bank account");
        bankAccount.setDescription("Description of bank account");
        bankAccount.setTotalBalance(BigDecimal.valueOf(2000));
        bankAccount.setActive(true);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(transactionId);
        transaction.setDescription("Description of transaction");
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransactionType(EnumTransactionType.CREDIT);
        transaction.setEventDate(LocalDate.now());
        transaction.setEventTime(LocalTime.now());
        transaction.setBankAccount(bankAccount);
        when(repository.findById(transactionId)).thenReturn(Optional.of(transaction));

        TransactionDto transactionDto = service.getById(transactionId.toString());

        assertThat(transactionDto.getId()).isEqualTo(transaction.getId().toString());
        assertThat(transactionDto.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(transactionDto.getAmount()).isEqualTo(transaction.getAmount());
        assertThat(transactionDto.getTransactionType()).isEqualTo(transaction.getTransactionType());
        assertThat(transactionDto.getEventDate()).isEqualTo(transaction.getEventDate());
        assertThat(transactionDto.getEventTime()).isEqualTo(transaction.getEventTime());
        assertThat(transactionDto.getBankAccountId()).isEqualTo(transaction.getBankAccount().getId().toString());
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a transaction with the given ID")
    void testGetByIdWhenNotFound() {
        UUID transactionId = UUID.randomUUID();

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.getById(transactionId.toString()))
                .withMessage("Transaction not found with id ".concat(transactionId.toString()));
    }

    @ParameterizedTest
    @DisplayName("Should call the repository method passing the received filters")
    @EnumSource(value = EnumTransactionType.class,
            names = {"CREDIT", "DEBIT"})
    void testGetAllByFilters(EnumTransactionType enumTransactionType) {
        FilterTransactionDto dto = new FilterTransactionDto();
        dto.setStartDate(LocalDate.of(2023, 1, 1));
        dto.setEndDate(LocalDate.of(2023, 12, 31));
        dto.setBankAccountId(UUID.randomUUID().toString());
        dto.setTransactionType(enumTransactionType);
        dto.setPageable(PageRequest.of(0, 10));

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(UUID.fromString(dto.getBankAccountId()));
        bankAccount.setName("Name of bank account");
        bankAccount.setDescription("Description of bank account");
        bankAccount.setTotalBalance(BigDecimal.valueOf(2000));
        bankAccount.setActive(true);

        TransactionEntity transaction = new TransactionEntity();
        transaction.setId(UUID.randomUUID());
        transaction.setDescription("Description of transaction");
        transaction.setAmount(BigDecimal.valueOf(2000));
        transaction.setTransactionType(enumTransactionType);
        transaction.setEventDate(LocalDate.now());
        transaction.setEventTime(LocalTime.now());
        transaction.setBankAccount(bankAccount);
        List<TransactionEntity> transactions = List.of(transaction);

        when(repository.findAllByFilters(dto.getStartDate(),
                dto.getEndDate(),
                FinanceUtils.stringToUuidOrNull(dto.getBankAccountId()),
                dto.getTransactionType(),
                dto.getPageable()))
                .thenReturn(new PageImpl<>(transactions));

        service.getAllByFilters(dto);

        verify(repository).findAllByFilters(dto.getStartDate(),
                dto.getEndDate(),
                FinanceUtils.stringToUuidOrNull(dto.getBankAccountId()),
                dto.getTransactionType(),
                dto.getPageable());
    }

}