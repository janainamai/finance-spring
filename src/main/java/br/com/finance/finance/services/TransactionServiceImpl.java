package br.com.finance.finance.services;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.components.factory.TransactionComponentFactory;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.dto.FilterTransactionDto;
import br.com.finance.finance.services.dto.TransactionDto;
import br.com.finance.finance.services.interfaces.BankAccountService;
import br.com.finance.finance.services.interfaces.TransactionService;
import br.com.finance.finance.utils.FinanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static br.com.finance.finance.utils.FinanceConstants.INTEGER_TWO;
import static java.math.RoundingMode.HALF_UP;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionComponentFactory transactionComponentFactory;
    @Autowired
    private BankAccountService bankAccountService;

    @Override
    @Transactional(readOnly = true)
    public TransactionDto getById(String id) {
        TransactionEntity transaction = findByIdOrThrowObjectNotFound(id);

        return TransactionDto.fromEntity(transaction);
    }

    @Override
    @Transactional
    public void create(TransactionDto dto) {
        TransactionEntity savedTransaction = this.saveTransaction(dto);

        transactionComponentFactory.getStrategy(dto.getTransactionType())
                .updateTransactionValueInBankAccount(savedTransaction.getAmount(), dto.getBankAccountId());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionDto> getAllByFilters(FilterTransactionDto dto) {
        Page<TransactionEntity> transactionsPage = transactionRepository.findAllByFilters(
                dto.getStartDate(),
                dto.getEndDate(),
                FinanceUtils.stringToUuidOrNull(dto.getBankAccountId()),
                dto.getTransactionType(),
                dto.getPageable());

        return transactionsPage.map(TransactionDto::fromEntity);
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        TransactionEntity transaction = findByIdOrThrowObjectNotFound(id);
        transaction.setDeleted(true);
        transaction.setDeletedOn(LocalDateTime.now());
        transactionRepository.save(transaction);

        revertTransactionValueInBankingAccount(transaction);
    }

    private void revertTransactionValueInBankingAccount(TransactionEntity transaction) {
        EnumTransactionType transactionType = null;
        switch (transaction.getTransactionType()) {
            case DEBIT -> transactionType = EnumTransactionType.CREDIT;
            case CREDIT -> transactionType = EnumTransactionType.DEBIT;
        }

        transactionComponentFactory.getStrategy(transactionType)
                .updateTransactionValueInBankAccount(transaction.getAmount(), transaction.getBankAccount().getId().toString());
    }

    private TransactionEntity saveTransaction(TransactionDto dto) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount().setScale(INTEGER_TWO, HALF_UP));
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setEventDate(dto.getEventDate());
        transaction.setEventTime(dto.getEventTime());
        transaction.setBankAccount(bankAccountService.getEntityById(dto.getBankAccountId()));

        return transactionRepository.save(transaction);
    }

    private TransactionEntity findByIdOrThrowObjectNotFound(String id) {
        return transactionRepository.findById(FinanceUtils.stringToUuidOrThrowException(id))
                .orElseThrow(() -> new BadRequestException("Transaction not found with id ".concat(id)));
    }

}
