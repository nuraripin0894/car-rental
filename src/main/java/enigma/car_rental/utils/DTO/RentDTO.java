package enigma.car_rental.utils.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentDTO{
    @NotNull
    private Integer user_id;
    @NotNull
    private Integer car_id;
    private boolean completed;
    private Date started_at;
    private Date ends_at;
    private Integer rent_price;
}

