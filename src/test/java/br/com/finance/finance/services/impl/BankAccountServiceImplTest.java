package br.com.finance.finance.services.impl;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.BankAccountServiceImpl;
import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import br.com.finance.finance.services.dto.UpdateBankAccountDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl service;
    @Mock
    private BankAccountRepository repository;
    @Captor
    private ArgumentCaptor<BankAccountEntity> captorBank;

    @Test
    @DisplayName("Should return all records with their respective fields filled")
    void testGetAllWhenFound() {

        BankAccountEntity blueAccount = new BankAccountEntity();
        blueAccount.setId(UUID.randomUUID());
        blueAccount.setName("Bank account blue");
        blueAccount.setDescription("Description of blue bank account");
        blueAccount.setTotalBalance(BigDecimal.valueOf(250.65));
        blueAccount.setActive(true);

        BankAccountEntity greenAccount = new BankAccountEntity();
        greenAccount.setId(UUID.randomUUID());
        greenAccount.setName("Bank account green");
        greenAccount.setDescription("Description of green bank account");
        greenAccount.setTotalBalance(BigDecimal.valueOf(-100.65));
        greenAccount.setActive(false);

        when(repository.findAll()).thenReturn(List.of(greenAccount, blueAccount));

        List<BankAccountDto> accounts = service.getAll();

        BankAccountDto blueAccountDto = new BankAccountDto();
        blueAccountDto.setId(blueAccount.getId().toString());
        blueAccountDto.setName("Bank account blue");
        blueAccountDto.setDescription("Description of blue bank account");
        blueAccountDto.setTotalBalance(BigDecimal.valueOf(250.65));
        blueAccountDto.setActive(true);

        BankAccountDto greenAccountDto = new BankAccountDto();
        greenAccountDto.setId(greenAccount.getId().toString());
        greenAccountDto.setName("Bank account green");
        greenAccountDto.setDescription("Description of green bank account");
        greenAccountDto.setTotalBalance(BigDecimal.valueOf(-100.65));
        greenAccountDto.setActive(false);
        assertThat(accounts).containsAll(List.of(greenAccountDto, blueAccountDto));
    }

    @Test
    @DisplayName("Should return an empty list when no results are found")
    void testGetAllWhenNotFound() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<BankAccountDto> accounts = service.getAll();
        assertThat(accounts).isEmpty();
    }

    @Test
    @DisplayName("Should save the bank account in the database with the provided fields")
    void testCreate() {
        CreateBankAccountDto dto = new CreateBankAccountDto();
        dto.setName("Bank account name");
        dto.setDescription("Bank account description");

        service.create(dto);

        verify(repository).save(captorBank.capture());

        BankAccountEntity savedBank = captorBank.getValue();
        assertThat(savedBank.getName()).isEqualTo(dto.getName());
        assertThat(savedBank.getDescription()).isEqualTo(dto.getDescription());
    }

    @Test
    @DisplayName("Should save the bank account with an initial balance of zero")
    void testCreateSavesBalanceZero() {
        CreateBankAccountDto dto = new CreateBankAccountDto();
        dto.setName("Bank account name");
        dto.setDescription("Bank account description");

        service.create(dto);

        verify(repository).save(captorBank.capture());

        BankAccountEntity savedBank = captorBank.getValue();
        assertThat(savedBank.getTotalBalance()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Should save the bank account with the status set to true")
    void testCreateSavesActive() {
        CreateBankAccountDto dto = new CreateBankAccountDto();
        dto.setName("Bank account name");
        dto.setDescription("Bank account description");

        service.create(dto);

        verify(repository).save(captorBank.capture());

        BankAccountEntity savedBank = captorBank.getValue();
        assertThat(savedBank.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should update the bank account in the database with the provided fields")
    void testUpdate() {
        UUID bankAccountId = UUID.randomUUID();

        UpdateBankAccountDto dto = new UpdateBankAccountDto();
        dto.setId(bankAccountId.toString());
        dto.setName("Bank account name updated");
        dto.setDescription("Bank account description updated");
        dto.setActive(false);

        BankAccountEntity currentBankAccount = new BankAccountEntity();
        currentBankAccount.setId(bankAccountId);
        currentBankAccount.setName("Bank account name");
        currentBankAccount.setDescription("Bank account description");
        currentBankAccount.setActive(true);
        when(repository.findById(UUID.fromString(dto.getId()))).thenReturn(Optional.of(currentBankAccount));

        service.update(dto);

        verify(repository).save(captorBank.capture());

        BankAccountEntity updatedBank = captorBank.getValue();
        assertThat(updatedBank.getId()).hasToString(dto.getId());
        assertThat(updatedBank.getName()).isEqualTo(dto.getName());
        assertThat(updatedBank.getDescription()).isEqualTo(dto.getDescription());
        assertThat(updatedBank.isActive()).isFalse();
        assertThat(updatedBank.getTotalBalance()).isEqualTo(currentBankAccount.getTotalBalance());
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a bank account with the given ID")
    void testUpdateWhenNotFound() {
        UpdateBankAccountDto dto = new UpdateBankAccountDto();
        dto.setId(UUID.randomUUID().toString());

        when(repository.findById(UUID.fromString(dto.getId()))).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.update(dto))
                .withMessage("Bank account not found with id ".concat(dto.getId()));
    }

    @Test
    @DisplayName("Should return the found bank account")
    void testGetDtoByIdWhenFound() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(bankAccountId);
        bankAccount.setName("Bank account name");
        bankAccount.setDescription("Bank account description");
        bankAccount.setTotalBalance(BigDecimal.TEN);
        bankAccount.setVersion(5L);
        bankAccount.setActive(true);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        BankAccountDto bankAccountDto = service.getDtoById(bankAccountId.toString());
        assertThat(bankAccountDto.getName()).isEqualTo(bankAccount.getName());
        assertThat(bankAccountDto.getDescription()).isEqualTo(bankAccount.getDescription());
        assertThat(bankAccountDto.getTotalBalance()).isEqualTo(bankAccount.getTotalBalance());
        assertThat(bankAccountDto.getVersion()).isEqualTo(bankAccount.getVersion());
        assertThat(bankAccountDto.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a bank account with the given ID")
    void testGetDtoByIdWhenNotFound() {
        UUID bankAccountId = UUID.randomUUID();

        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.getEntityById(bankAccountId.toString()))
                .withMessage("Bank account not found with id ".concat(bankAccountId.toString()));
    }

    @Test
    @DisplayName("Should return the found bank account")
    void testGetEntityByIdWhenFound() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(bankAccountId);
        bankAccount.setName("Bank account name");
        bankAccount.setDescription("Bank account description");
        bankAccount.setTotalBalance(BigDecimal.TEN);
        bankAccount.setVersion(5L);
        bankAccount.setActive(true);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        BankAccountEntity bankAccountEntity = service.getEntityById(bankAccountId.toString());
        assertThat(bankAccountEntity.getName()).isEqualTo(bankAccount.getName());
        assertThat(bankAccountEntity.getDescription()).isEqualTo(bankAccount.getDescription());
        assertThat(bankAccountEntity.getTotalBalance()).isEqualTo(bankAccount.getTotalBalance());
        assertThat(bankAccountEntity.getVersion()).isEqualTo(bankAccount.getVersion());
        assertThat(bankAccountEntity.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a bank account with the given ID")
    void testGetEntityByIdWhenNotFound() {
        UUID bankAccountId = UUID.randomUUID();

        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.getEntityById(bankAccountId.toString()))
                .withMessage("Bank account not found with id ".concat(bankAccountId.toString()));
    }

    @Test
    @DisplayName("Should update the activate field of bank account of received identifier")
    void testDeactivateWhenFound() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(bankAccountId);
        bankAccount.setName("Bank account name");
        bankAccount.setDescription("Bank account description");
        bankAccount.setTotalBalance(BigDecimal.TEN);
        bankAccount.setActive(true);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        service.deactivate(bankAccountId.toString());

        verify(repository).save(captorBank.capture());

        BankAccountEntity savedBankAccount = captorBank.getValue();
        assertThat(savedBankAccount.getName()).isEqualTo(bankAccount.getName());
        assertThat(savedBankAccount.getDescription()).isEqualTo(bankAccount.getDescription());
        assertThat(savedBankAccount.getTotalBalance()).isEqualTo(bankAccount.getTotalBalance());
        assertThat(savedBankAccount.isActive()).isFalse();
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a bank account with the given ID")
    void testDeactivateWhenNotFound() {
        UUID bankAccountId = UUID.randomUUID();

        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.deactivate(bankAccountId.toString()))
                        .withMessage("Bank account not found with id ".concat(bankAccountId.toString()));

        verify(repository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should update the activate field of bank account of received identifier")
    void testActivateWhenFound() {
        UUID bankAccountId = UUID.randomUUID();

        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setId(bankAccountId);
        bankAccount.setName("Bank account name");
        bankAccount.setDescription("Bank account description");
        bankAccount.setTotalBalance(BigDecimal.TEN);
        bankAccount.setActive(true);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));

        service.activate(bankAccountId.toString());

        verify(repository).save(captorBank.capture());

        BankAccountEntity savedBankAccount = captorBank.getValue();
        assertThat(savedBankAccount.getName()).isEqualTo(bankAccount.getName());
        assertThat(savedBankAccount.getDescription()).isEqualTo(bankAccount.getDescription());
        assertThat(savedBankAccount.getTotalBalance()).isEqualTo(bankAccount.getTotalBalance());
        assertThat(savedBankAccount.isActive()).isTrue();
    }

    @Test
    @DisplayName("Should throw an exception when it doesn't find a bank account with the given ID")
    void testActivateWhenNotFound() {
        UUID bankAccountId = UUID.randomUUID();

        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> service.activate(bankAccountId.toString()))
                .withMessage("Bank account not found with id ".concat(bankAccountId.toString()));

        verify(repository, times(0)).save(any());
    }

}