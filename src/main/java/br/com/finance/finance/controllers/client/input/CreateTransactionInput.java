package br.com.finance.finance.controllers.client.input;

import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.services.dto.TransactionDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateTransactionInput {

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "amount is required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;

    @NotNull(message = "transactionType is required")
    private EnumTransactionType transactionType;

    @NotNull(message = "eventDate is required")
    private LocalDate eventDate;

    @NotNull(message = "eventTime is required")
    private LocalTime eventTime;

    @NotBlank(message = "bankAccountId is required")
    private String bankAccountId;

    public TransactionDto toDto() {
        TransactionDto dto = new TransactionDto();
        dto.setDescription(description);
        dto.setAmount(this.amount);
        dto.setTransactionType(this.transactionType);
        dto.setEventDate(this.eventDate);
        dto.setEventTime(this.eventTime);
        dto.setBankAccountId(this.bankAccountId);

        return dto;
    }

}
