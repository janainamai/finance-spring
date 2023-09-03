package br.com.finance.finance.services.dto;

import br.com.finance.finance.domain.enums.EnumTransactionType;
import br.com.finance.finance.domain.entities.TransactionEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
public class TransactionDto {

    private String id;
    private String description;
    private BigDecimal amount;
    private EnumTransactionType transactionType;
    private LocalDate eventDate;
    private LocalTime eventTime;
    private String bankAccountId;
    private String bankAccountName;
    private Boolean deleted;
    private LocalDateTime deletedOn;

    public static TransactionDto fromEntity(TransactionEntity entity) {
        TransactionDto dto = new TransactionDto();
        dto.setId(entity.getId().toString());
        dto.setDescription(entity.getDescription());
        dto.setAmount(entity.getAmount());
        dto.setTransactionType(entity.getTransactionType());
        dto.setEventDate(entity.getEventDate());
        dto.setEventTime(entity.getEventTime());
        dto.setBankAccountId(entity.getBankAccount().getId().toString());
        dto.setBankAccountName(entity.getBankAccount().getName());
        dto.setDeleted(entity.isDeleted());
        dto.setDeletedOn(entity.getDeletedOn());

        return dto;
    }

    public static List<TransactionDto> fromEntities(List<TransactionEntity> transactions) {
        return transactions.stream().map(TransactionDto::fromEntity).toList();
    }

}
