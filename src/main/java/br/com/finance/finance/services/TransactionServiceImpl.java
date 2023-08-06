package br.com.finance.finance.services;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.components.interfaces.TransactionComponent;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.interfaces.TransactionService;
import br.com.finance.finance.services.dto.TransactionDto;
import br.com.finance.finance.utils.FinanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionComponent transactionComponent;

    @Override
    @Transactional(readOnly = true)
    public TransactionDto getById(String id) {
        TransactionEntity transaction = findByIdOrThrowObjectNotFound(id);

        return TransactionDto.fromEntity(transaction);
    }

    @Override
    @Transactional
    public void create(TransactionDto dto) {
        TransactionEntity transactionEntity = transactionComponent.saveTransaction(dto);
        transactionComponent.updateTransactionValueInBankAccount(transactionEntity, dto.getBankAccountId());
    }

    private TransactionEntity findByIdOrThrowObjectNotFound(String id) {
        return transactionRepository.findById(FinanceUtils.stringToUUID(id))
                .orElseThrow(() -> new BadRequestException("Transaction not found with id ".concat(id)));
    }

}
