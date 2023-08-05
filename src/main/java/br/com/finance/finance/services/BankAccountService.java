package br.com.finance.finance.services;

import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import br.com.finance.finance.services.dto.UpdateBankAccountDto;

import java.util.List;
import java.util.UUID;

public interface BankAccountService {
    List<BankAccountDto> getAll();

    void create(CreateBankAccountDto dto);

    BankAccountDto getById(UUID uuid);

    void update(UpdateBankAccountDto dto);
}
