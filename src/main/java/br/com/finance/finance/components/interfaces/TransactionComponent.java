package br.com.finance.finance.components.interfaces;

import br.com.finance.finance.domain.enums.EnumTransactionType;

import java.math.BigDecimal;

public interface TransactionComponent extends TransactionStrategy<EnumTransactionType> {

    void updateTransactionValueInBankAccount(BigDecimal amount, String bankAccountId);

}
