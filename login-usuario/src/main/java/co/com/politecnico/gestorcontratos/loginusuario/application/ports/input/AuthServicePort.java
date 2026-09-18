package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input;

import java.util.Map;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;

public interface AuthServicePort {
    Map<String, String> auth(LoginRequest request);
    Map<String, String> extractSubject(String token);
    String generateRefreshToken(String userId);
    boolean isAccessTokenValid(String token);
    boolean isRefreshTokenValid(String token);
}
