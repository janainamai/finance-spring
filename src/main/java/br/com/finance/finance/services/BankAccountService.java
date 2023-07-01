package br.com.finance.finance.services;

import br.com.finance.finance.domain.entities.BankAccount;

import java.util.List;

public interface BankAccountService {
    List<BankAccount> retrieveAll();
}
