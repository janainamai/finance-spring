package br.com.finance.finance.services.impl;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.dto.BankAccountDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {

    @InjectMocks
    private BankAccountServiceImpl service;
    @Mock
    private BankAccountRepository repository;

    @Test
    void testRetrieveAllWhenFound() {

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

        List<BankAccountDto> accounts = service.retrieveAll();

        BankAccountDto blueAccountDto = new BankAccountDto();
        blueAccountDto.setName("Bank account blue");
        blueAccountDto.setDescription("Description of blue bank account");
        blueAccountDto.setTotalBalance(BigDecimal.valueOf(250.65));
        blueAccountDto.setActive(true);

        BankAccountDto greenAccountDto = new BankAccountDto();
        greenAccountDto.setName("Bank account green");
        greenAccountDto.setDescription("Description of green bank account");
        greenAccountDto.setTotalBalance(BigDecimal.valueOf(-100.65));
        greenAccountDto.setActive(false);
        assertThat(accounts).containsAll(List.of(greenAccountDto, blueAccountDto));
    }

    @Test
    void testRetrieveAllWhenNotFound() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<BankAccountDto> accounts = service.retrieveAll();
        assertThat(accounts).isEmpty();
    }

}