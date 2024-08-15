package enigma.car_rental.config;

import enigma.car_rental.service.implementation.UserEntityServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserEntityServiceImplementation userEntityServiceImplementation;

    @Autowired
    private JWTAuthFilter jwtAuthFilter;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/auth/register").permitAll()
//                        .requestMatchers("/api/auth/login").permitAll()
//                        .requestMatchers(HttpMethod.POST,"/api/auth/refresh").hasAnyAuthority("ADMIN", "USER")
//                        .requestMatchers(HttpMethod.GET,"/users").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/users/**").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/users/**").hasAnyAuthority("USER")
//                        .requestMatchers(HttpMethod.DELETE,"/users").hasAnyAuthority("USER")
//                        .requestMatchers(HttpMethod.DELETE,"/users/**").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/brands").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/brands").permitAll()
//                        .requestMatchers(HttpMethod.GET,"/brands/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/brands/**").hasAnyAuthority( "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/brands/**").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/cars").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/cars").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/cars/**").hasAnyAuthority("USER", "ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/cars/**").hasAnyAuthority( "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/cars/**").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.POST,"/rents").hasAnyAuthority("USER")
//                        .requestMatchers(HttpMethod.GET,"/rents").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/rents/**").hasAnyAuthority("ADMIN")
//                        .requestMatchers(HttpMethod.PUT,"/rents/**").hasAnyAuthority( "ADMIN")
//                        .requestMatchers(HttpMethod.DELETE,"/rents/**").hasAnyAuthority("ADMIN")
                        .anyRequest().permitAll())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthFilter, UsernamePasswordAuthenticationFilter.class
                );
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userEntityServiceImplementation);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
