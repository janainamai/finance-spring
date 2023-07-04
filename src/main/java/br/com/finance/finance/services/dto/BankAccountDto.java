package br.com.finance.finance.services.dto;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountDto {

    private String name;
    private String description;
    private boolean active;
    private BigDecimal totalBalance;

    public static BankAccountDto fromEntity(BankAccountEntity entity) {
        BankAccountDto dto = new BankAccountDto();
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setActive(entity.isActive());
        dto.setTotalBalance(entity.getTotalBalance());

        return dto;
    }

    public static List<BankAccountDto> fromEntities(List<BankAccountEntity> bankAccounts) {
        return bankAccounts.stream().map(BankAccountDto::fromEntity).toList();
    }

}
