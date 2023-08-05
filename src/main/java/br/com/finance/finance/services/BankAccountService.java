package br.com.finance.finance.services;

import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import br.com.finance.finance.services.dto.UpdateBankAccountDto;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDto> getAll();

    void create(CreateBankAccountDto dto);

    BankAccountDto getById(String id);

    void update(UpdateBankAccountDto dto);

    void deactivate(String id);

    void activate(String id);
}
