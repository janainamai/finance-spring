package br.com.finance.finance.services.interfaces;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import br.com.finance.finance.services.dto.UpdateBankAccountDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BankAccountService {

    List<BankAccountDto> getAll();

    void create(CreateBankAccountDto dto);

    BankAccountDto getDtoById(String id);

    void update(UpdateBankAccountDto dto);

    void deactivate(String id);

    void activate(String id);

    BankAccountEntity getEntityById(String bankAccountId);
}
