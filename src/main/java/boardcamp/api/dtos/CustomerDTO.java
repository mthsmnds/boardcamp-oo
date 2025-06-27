package boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String cpf;
}
