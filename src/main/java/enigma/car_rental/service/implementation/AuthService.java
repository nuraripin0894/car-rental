package enigma.car_rental.service.implementation;

import enigma.car_rental.model.UserEntity;
import enigma.car_rental.repository.UserEntityRepository;
import enigma.car_rental.utils.DTO.AuthResponDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponDTO signUp(AuthResponDTO regitrationRequest){
        AuthResponDTO resp = new AuthResponDTO();

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(regitrationRequest.getUsername());
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=.*[0-9])[a-zA-Z0-9!@#$%^&*]{12,20}$";
        if (!regitrationRequest.getPassword().matches(regex)){
            throw new RuntimeException("not strong enough!");
        }
        userEntity.setPassword(passwordEncoder.encode(regitrationRequest.getPassword()));
        userEntity.setRole("USER");
        UserEntity userResult = userEntityRepository.insertUser(userEntity);
        if(userResult.getId() > 0){
            resp.setMessage("Register success!");
            resp.setId(userResult.getId());
            resp.setUsername(userResult.getUsername());
        }

        return resp;
    }

    public AuthResponDTO signIn(AuthResponDTO signinRequest) {
        UserEntity user;

        if (signinRequest.getUsername() == null) {
            throw new RuntimeException("username login required");
        } else {
            user = userEntityRepository.findByUsername(signinRequest.getUsername());
            if(user == null){
                throw new RuntimeException("username not found");
            }
        }

        if (signinRequest.getPassword() == null) {
            throw new RuntimeException("password login required");
        } else{
            if(!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())){
                System.out.println(user.getPassword());
                throw new RuntimeException("password not match");
            }
        }

        AuthResponDTO responDTO = new AuthResponDTO();

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        var jwt = jwtUtils.generateToken(user);
        var refJwt = jwtUtils.generateRefreshToken(new HashMap<>(), user);
        responDTO.setMessage("Login Success!");
        responDTO.setAccessToken(jwt);
        responDTO.setRefreshToken(refJwt);

        return responDTO;
    }

    public AuthResponDTO refreshToken(AuthResponDTO refreshTokenRequest){
        AuthResponDTO responDTO = new AuthResponDTO();
        String ourUsername = jwtUtils.extractUsername(refreshTokenRequest.getAccessToken());
        UserEntity users = userEntityRepository.findByUsername(ourUsername);
        if(jwtUtils.isTokenValid(refreshTokenRequest.getAccessToken(), users)) {
            var jwt = jwtUtils.generateToken(users);
            responDTO.setAccessToken(jwt);
        }
        else{
            throw new RuntimeException("Please log in again.");
        }

        return responDTO;
    }
}
