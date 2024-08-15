package enigma.car_rental.utils.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CarDTO {
    @NotNull
    private Integer brand_id;
    private String name;
    private Boolean available;
    private Integer price;
}