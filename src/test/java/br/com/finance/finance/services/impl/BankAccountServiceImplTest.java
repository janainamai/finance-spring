package br.com.finance.finance.services.impl;

import br.com.finance.finance.domain.entities.BankAccount;
import br.com.finance.finance.repositories.BankAccountRepository;
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

        BankAccount blueAccount = new BankAccount();
        blueAccount.setId(UUID.randomUUID());
        blueAccount.setName("Bank account blue");
        blueAccount.setDescription("Description of blue bank account");
        blueAccount.setTotalBalance(BigDecimal.valueOf(250.65));
        blueAccount.setActive(true);

        BankAccount greenAccount = new BankAccount();
        greenAccount.setId(UUID.randomUUID());
        greenAccount.setName("Bank account green");
        greenAccount.setDescription("Description of green bank account");
        greenAccount.setTotalBalance(BigDecimal.valueOf(-100.65));
        greenAccount.setActive(false);

        when(repository.findAll()).thenReturn(List.of(greenAccount, blueAccount));

        List<BankAccount> accounts = service.retrieveAll();
        assertThat(accounts).containsAll(List.of(greenAccount, blueAccount));
    }

    @Test
    void testRetrieveAllWhenNotFound() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<BankAccount> accounts = service.retrieveAll();
        assertThat(accounts).isEmpty();
    }

}