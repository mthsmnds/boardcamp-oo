package boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GameDTO {
    @NotBlank
    private String name;

    @NotNull
    @Positive
    private int stockTotal;

    @NotNull
    @Positive
    private int pricePerDay;
}
