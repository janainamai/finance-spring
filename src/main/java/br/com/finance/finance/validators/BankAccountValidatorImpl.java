package br.com.finance.finance.validators;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BankAccountValidatorImpl implements BankAccountValidator {

    @Autowired
    private BankAccountRepository repository;

    @Override
    public void validateNameExists(String name) {
        if (repository.existsByName(name)) {
            throw new BadRequestException("Already exists a bank account with this name");
        }
    }

}
