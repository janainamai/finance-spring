package br.com.finance.finance.controllers.client.input;

import br.com.finance.finance.services.dto.CreateBankAccountDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateBankAccountInput {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    public CreateBankAccountDto toDto() {
        CreateBankAccountDto dto = new CreateBankAccountDto();
        dto.setName(name);
        dto.setDescription(description);

        return dto;
    }

}
