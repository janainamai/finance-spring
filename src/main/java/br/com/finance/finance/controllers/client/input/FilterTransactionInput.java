package br.com.finance.finance.controllers.client.input;

import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.services.dto.FilterTransactionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

@Data
public class FilterTransactionInput {

    @NotNull(message = "startDate is required")
    private LocalDate startDate;

    @NotNull(message = "endDate is required")
    private LocalDate endDate;

    @NotBlank(message = "bankAccountId is required")
    private String bankAccountId;

    private EnumTransactionType transactionType;

    @NotNull(message = "page is required")
    private Integer page;

    @NotNull(message = "size is required")
    private Integer size;

    public FilterTransactionDto toDto() {
        FilterTransactionDto dto = new FilterTransactionDto();
        dto.setStartDate(this.startDate);
        dto.setEndDate(this.endDate);
        dto.setBankAccountId(this.bankAccountId);
        dto.setTransactionType(this.transactionType);
        dto.setPageable(PageRequest.of(page, size));

        return dto;
    }

}
