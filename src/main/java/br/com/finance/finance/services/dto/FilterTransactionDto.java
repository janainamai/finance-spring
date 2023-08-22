package br.com.finance.finance.services.dto;

import br.com.finance.finance.domain.enums.EnumTransactionType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@Data
public class FilterTransactionDto {

    private LocalDate startDate;
    private LocalDate endDate;
    private String bankAccountId;
    private EnumTransactionType transactionType;
    private Pageable pageable;

}
