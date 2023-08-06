package br.com.finance.finance.services.dto;

import br.com.finance.finance.domain.entities.BankAccountEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountDto {

    private String id;
    private String name;
    private String description;
    private boolean active;
    private BigDecimal totalBalance;
    private Long version;

    public static BankAccountDto fromEntity(BankAccountEntity entity) {
        BankAccountDto dto = new BankAccountDto();
        dto.setId(entity.getId().toString());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setTotalBalance(entity.getTotalBalance());
        dto.setActive(entity.isActive());
        dto.setVersion(entity.getVersion());

        return dto;
    }

    public static List<BankAccountDto> fromEntities(List<BankAccountEntity> bankAccounts) {
        return bankAccounts.stream().map(BankAccountDto::fromEntity).toList();
    }

}
