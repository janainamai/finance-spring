package br.com.finance.finance.components.interfaces;

import br.com.finance.finance.domain.enums.EnumTransactionType;

import java.math.BigDecimal;

public interface TransactionStrategy {

    EnumTransactionType getTransactionType();

    void updateTransactionValueInBankAccount(BigDecimal amount, String bankAccountId);

}
