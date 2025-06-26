package boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GameDTO {
    @NotBlank
    private String name;

    @NotNull
    private int stockTotal;

    @NotNull
    private int pricePerDay;
}
