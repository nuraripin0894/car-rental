package enigma.car_rental.controller;

import enigma.car_rental.utils.Response;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e){
        String message = e.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(message.contains("name") && message.contains("model.Brand")){
            message = "Brand name cannot be blank!";
        }
        if(message.contains("name") && message.contains("model.Car")){
            message = "Car name cannot be blank!";
        }
        if(message.contains("price") && message.contains("model.Car")){
            message = "Car price cannot be blank!";
        }
        if(message.contains("name") && message.contains("model.User")){
            message = "User name cannot be blank!";
        }
        if(message.contains("balance") && message.contains("model.User")){
            message = "Balance cannot be blank!";
        }

        return Response.renderJson(null, message, status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e){
        String message = e.getMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if(message.contains("Brand with id")){
            message = "Brand not found!";
        }
        if(message.contains("Car with id")){
            message = "Car not found!";
        }
        if(message.contains("Rent with id")){
            message = "Rent not found!";
        }
        if(message.contains("User with id")){
            message = "User not found!";
        }
        if(message.contains("startedTemp")){
            message = "Rent started_at cannot be blank!";
        }
        if(message.contains("endsTemp")){
            message = "Rent ends_at cannot be blank!";
        }
        if(message.contains("Car is not available")){
            message = "Car is not available for rent!";
        }
        if(message.contains("user_id empty")){
            message = "Rent user_id cannot be blank";
        }
        if(message.contains("car_id empty")){
            message = "Rent car_id cannot be blank";
        }
        if(message.contains("brand_id empty")){
            message = "Car brand_id cannot be blank";
        }
        if(message.contains("delete from cars") && message.contains("foreign key constraint")){
            message = "Car cannot be deleted (FK constraint)";
        }
        if(message.contains("delete from brands") && message.contains("foreign key constraint")){
            message = "Brand cannot be deleted (FK constraint)";
        }
        if(message.contains("delete from users") && message.contains("foreign key constraint")){
            message = "User cannot be deleted (FK constraint)";
        }
        if(message.contains("Please log in again.")){
            message = "Your token is invalid or expired. Please log in again.";
        }
        if(message.contains("Key (username)")){
            message = "Username already exist, please use different username.";
        }
        if(message.contains("duplicate") && message.contains("cars")){
            message = "Car name already exist, please use different car name.";
        }
        if(message.contains("duplicate") && message.contains("brands")){
            message = "Brand name already exist, please use different brand name.";
        }
        if(message.contains("username login")){
            message = "Username is required to login.";
        }
        if(message.contains("password login")){
            message = "Password is required to login.";
        }
        if(message.contains("password not match")){
            message = "Invalid credentials, password not match";
        }
        if(message.contains("not strong enough!")){
            message = "Password is not strong enough! " +
                    "(password must contain number, symbol, upper & lower case, " +
                    "should longer than 12 character (20 at max)";
        }
        if(message.contains("Bad credentials") || message.contains("UserDetailsService returned null")){
            message = "Invalid username or password!";
        }

        return Response.renderJson(null, message, status);
    }
}
