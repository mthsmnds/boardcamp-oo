package boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RentalDTO {

    @NotNull
    private Long customerId;

    @NotNull
    private Long gameId;

    @NotNull
    @Positive
    private int daysRented;
}
