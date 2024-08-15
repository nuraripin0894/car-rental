package enigma.car_rental.utils.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import enigma.car_rental.model.UserEntity;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponDTO {
    private String message;
    private Integer id;
    private String username;
    private String password;
    private String accessToken;
    private String refreshToken;
    private UserEntity users;
}

