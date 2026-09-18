package co.com.politecnico.gestorcontratos.loginusuario.application.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.AuthServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.JWTServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;

@Service 
public class AuthService implements AuthServicePort {

    private final UserServicePort userService;
    private final JWTServicePort jwtService;

    public AuthService(UserServicePort userService, JWTServicePort jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Map<String, String> auth(LoginRequest request) {
        UserDTO userDto = userService.getByEmail(request.email());

        if (userDto != null && userDto.email().equals(request.email())
                && userService.matches(request.pass(), userDto.pass())) {
            
            String accessToken = jwtService.generateAccessToken(userDto.id());
            String refreshToken = jwtService.generateRefreshToken(userDto.id());

            return Map.of(
                    "message", "Approved access",
                    "accessToken", accessToken,
                    "refreshToken", refreshToken,
                    "tokenType", "Bearer");
        }
        return null;
    }

    @Override
    public Map<String, String> extractSubject(String token) {
        UserDTO user = userService.getById(jwtService.extractSubject(token));
        return Map.of("id", user.id(), "name", user.name(), "email", user.email());
    }

    @Override
    public boolean isAccessTokenValid(String token) {
        return jwtService.isAccessTokenValid(token);
    }

    @Override
    public boolean isRefreshTokenValid(String token) {
        return jwtService.isAccessTokenValid(token);
    }

    @Override
    public String generateRefreshToken(String userId) {
        return jwtService.generateRefreshToken(userId);
    }
}
