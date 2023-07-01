package br.com.finance.finance.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankAccountOutput {

    private String name;
    private String description;
    private boolean active;
    private BigDecimal totalBalance;

}
