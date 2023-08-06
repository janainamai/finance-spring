package br.com.finance.finance.components;

import br.com.finance.authentication.infra.exception.BadRequestException;
import br.com.finance.finance.components.interfaces.TransactionComponent;
import br.com.finance.finance.components.interfaces.TransactionStrategy;
import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.TransactionRepository;
import br.com.finance.finance.services.interfaces.BankAccountService;
import br.com.finance.finance.services.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static br.com.finance.finance.utils.FinanceConstants.INTEGER_TWO;
import static java.math.RoundingMode.HALF_UP;

@Component
public class TransactionComponentImpl implements TransactionComponent {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private BankAccountService bankAccountService;

    private final Map<EnumTransactionType, TransactionStrategy> strategies;

    @Autowired
    public TransactionComponentImpl(Map<String, TransactionStrategy> strategyMap) {
        strategies = strategyMap.values().stream()
                .collect(Collectors.toMap(TransactionStrategy::getTransactionType, Function.identity()));
    }

    @Override
    @Transactional
    public TransactionEntity saveTransaction(TransactionDto dto) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setDescription(dto.getDescription());
        transaction.setAmount(dto.getAmount().setScale(INTEGER_TWO, HALF_UP));
        transaction.setTransactionType(dto.getTransactionType());
        transaction.setEventDate(dto.getEventDate());
        transaction.setEventTime(dto.getEventTime());
        transaction.setBankAccount(bankAccountService.getEntityById(dto.getBankAccountId()));

        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void updateTransactionValueInBankAccount(TransactionEntity transactionEntity, String bankAccountId) {
        TransactionStrategy transactionStrategy = getTransactionStrategy(transactionEntity.getTransactionType());

        transactionStrategy.updateTransactionValueInBankAccount(transactionEntity.getAmount(), bankAccountId);
    }

    private TransactionStrategy getTransactionStrategy(EnumTransactionType transactionType) {
        TransactionStrategy transactionStrategy = strategies.get(transactionType);
        if (Objects.isNull(transactionStrategy)) {
            throw new BadRequestException("Invalid transaction type: ".concat(transactionType.toString()));
        }
        return transactionStrategy;
    }

}
