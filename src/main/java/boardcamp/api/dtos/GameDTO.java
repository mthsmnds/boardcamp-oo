package boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
