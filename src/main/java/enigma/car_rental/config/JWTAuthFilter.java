package enigma.car_rental.config;

import enigma.car_rental.service.UserEntityService;
import enigma.car_rental.service.implementation.JWTUtils;
import enigma.car_rental.service.implementation.UserEntityServiceImplementation;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserEntityServiceImplementation userEntityServiceImplementation;

    @SneakyThrows
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain){
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userUsername;
        if(authHeader == null || authHeader.isBlank()){
            filterChain.doFilter(request, response);
            return;
        }
        jwtToken = authHeader.substring(7);
        userUsername = jwtUtils.extractUsername(jwtToken);
        if(userUsername != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userEntityServiceImplementation.loadUserByUsername(userUsername);
            if(jwtUtils.isTokenValid(jwtToken, userDetails)){
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(token);
                SecurityContextHolder.setContext(securityContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
