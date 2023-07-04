package br.com.finance.finance.controllers.client.output;

import br.com.finance.finance.services.dto.BankAccountDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class BankAccountOutput {

    private String name;
    private String description;
    private boolean active;
    private BigDecimal totalBalance;

    public static BankAccountOutput fromDto(BankAccountDto dto) {
        BankAccountOutput output = new BankAccountOutput();
        output.setName(dto.getName());
        output.setDescription(dto.getDescription());
        output.setActive(dto.isActive());
        output.setTotalBalance(dto.getTotalBalance());

        return output;
    }

    public static List<BankAccountOutput> fromDtos(List<BankAccountDto> dtos) {
        return dtos.stream().map(BankAccountOutput::fromDto).toList();
    }

}
