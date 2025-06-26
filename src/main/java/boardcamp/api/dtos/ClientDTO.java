package boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDTO {
    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @NotBlank
    private String cpf;
}
