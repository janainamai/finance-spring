package br.com.finance.finance.services.impl;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.BankAccountService;
import br.com.finance.finance.services.dto.BankAccountDto;
import br.com.finance.finance.services.dto.CreateBankAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
    public BankAccountDto getById(UUID bankAccountId) {
        BankAccountEntity bankAccount = repository.findById(bankAccountId)
                .orElseThrow(() -> new BadRequestException("Bank account not found with id ".concat(bankAccountId.toString())));

        return BankAccountDto.fromEntity(bankAccount);
    }

}
