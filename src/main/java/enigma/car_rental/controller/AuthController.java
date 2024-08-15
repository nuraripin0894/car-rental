package enigma.car_rental.controller;

import enigma.car_rental.service.implementation.AuthService;
import enigma.car_rental.utils.DTO.AuthResponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Cacheable
    @PostMapping("/register")
    public ResponseEntity<AuthResponDTO> signUp(@RequestBody AuthResponDTO signUpRequest){
        AuthResponDTO createdUser = authService.signUp(signUpRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }

    @Cacheable
    @PostMapping("/login")
    public ResponseEntity<AuthResponDTO> signIn(@RequestBody AuthResponDTO signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }

    @Cacheable
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponDTO> refeshToken(@RequestBody AuthResponDTO refreshTokenRequest){
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }
}
