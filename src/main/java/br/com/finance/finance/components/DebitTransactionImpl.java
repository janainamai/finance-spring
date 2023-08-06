package br.com.finance.finance.components;

import br.com.finance.finance.components.interfaces.TransactionStrategy;
import br.com.finance.finance.domain.entities.BankAccountEntity;
import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.repositories.BankAccountRepository;
import br.com.finance.finance.services.interfaces.BankAccountService;
import br.com.finance.finance.utils.FinanceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static br.com.finance.finance.utils.FinanceConstants.INTEGER_TWO;
import static java.math.RoundingMode.HALF_UP;

@Component("DebitTransactionImpl")
public class DebitTransactionImpl implements TransactionStrategy {

    @Autowired
    private BankAccountService bankAccountService;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Override
    public EnumTransactionType getTransactionType() {
        return EnumTransactionType.DEBIT;
    }

    @Override
    @Transactional
    public void updateTransactionValueInBankAccount(BigDecimal amount, String bankAccountId) {
        int maxAttempts = FinanceConstants.INTEGER_FIVE;
        int attempts = FinanceConstants.ZERO;

        while (attempts < maxAttempts) {
            try {
                BankAccountEntity bankAccount = bankAccountService.getEntityById(bankAccountId);

                BigDecimal totalBalance = bankAccount.getTotalBalance().subtract(amount).setScale(INTEGER_TWO, HALF_UP);
                bankAccount.setTotalBalance(totalBalance);

                bankAccountRepository.save(bankAccount);
                break;
            } catch (ObjectOptimisticLockingFailureException exception) {
                attempts++;
            }
        }
    }

}
