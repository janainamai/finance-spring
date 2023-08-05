package br.com.finance.finance.services.dto;

import lombok.Data;

@Data
public class UpdateBankAccountDto {

    private String id;

    private String name;

    private String description;

    private Boolean active;

}
