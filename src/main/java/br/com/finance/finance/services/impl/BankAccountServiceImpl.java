package br.com.finance.finance.services.impl;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.BankAccountService;
import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import br.com.finance.finance.services.dto.UpdateBankAccountDto;
import br.com.finance.finance.utils.FinanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<BankAccountDto> getAll() {
        List<BankAccountEntity> bankAccounts = repository.findAll();

        return BankAccountDto.fromEntities(bankAccounts);
    }

    @Override
    @Transactional
    public void create(CreateBankAccountDto dto) {
        BankAccountEntity bankAccount = new BankAccountEntity();
        bankAccount.setName(dto.getName());
        bankAccount.setDescription(dto.getDescription());
        bankAccount.setTotalBalance(BigDecimal.ZERO);
        bankAccount.setActive(true);

        repository.save(bankAccount);
    }

    @Override
    @Transactional
    public void update(UpdateBankAccountDto dto) {
        BankAccountEntity bankAccount = findByIdOrThrowObjectNotFound(dto.getId());

        bankAccount.setName(dto.getName());
        bankAccount.setDescription(dto.getDescription());
        bankAccount.setActive(dto.getActive());

        repository.save(bankAccount);
    }

    @Override
    @Transactional
    public void deactivate(String id) {
        BankAccountEntity bankAccount = findByIdOrThrowObjectNotFound(id);
        bankAccount.setActive(false);

        repository.save(bankAccount);
    }

    @Override
    @Transactional
    public void activate(String id) {
        BankAccountEntity bankAccount = findByIdOrThrowObjectNotFound(id);
        bankAccount.setActive(true);

        repository.save(bankAccount);
    }

    @Override
    public BankAccountDto getById(String bankAccountId) {
        BankAccountEntity bankAccount = findByIdOrThrowObjectNotFound(bankAccountId);

        return BankAccountDto.fromEntity(bankAccount);
    }

    private BankAccountEntity findByIdOrThrowObjectNotFound(String id) {
        return repository.findById(FinanceUtils.stringToUUID(id))
                .orElseThrow(() -> new BadRequestException("Bank account not found with id ".concat(id)));
    }

}
