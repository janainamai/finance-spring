package br.com.finance.authentication.domain.enums;

public enum EnumTransactionType {

    CREDIT("Credit"),
    DEBIT("Debit");

    private final String description;

    EnumTransactionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
