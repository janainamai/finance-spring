package br.com.finance.finance.controllers.client.input;

import br.com.finance.finance.services.dto.UpdateBankAccountDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBankAccountInput {

    @NotBlank(message = "id is required")
    private String id;

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "description is required")
    private String description;

    @NotNull(message = "active is required")
    private Boolean active;

    public UpdateBankAccountDto toDto() {
        UpdateBankAccountDto dto = new UpdateBankAccountDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setDescription(this.description);
        dto.setActive(this.active);

        return dto;
    }

}
