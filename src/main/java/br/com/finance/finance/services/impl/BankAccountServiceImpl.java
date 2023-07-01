package br.com.finance.finance.services.impl;

import br.com.finance.finance.domain.entities.BankAccount;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<BankAccount> retrieveAll() {
        return repository.findAll();
    }

}
