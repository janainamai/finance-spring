package br.com.finance.finance.services;

import br.com.finance.finance.services.dto.BankAccountDto;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> retrieveAll();
}
