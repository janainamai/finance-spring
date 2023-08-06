package br.com.finance.finance.components.interfaces;

import br.com.finance.finance.domain.entities.TransactionEntity;
import br.com.finance.finance.services.dto.TransactionDto;

public interface TransactionComponent {

    TransactionEntity saveTransaction(TransactionDto dto);

    void updateTransactionValueInBankAccount(TransactionEntity transactionEntity, String bankAccountId);

}
