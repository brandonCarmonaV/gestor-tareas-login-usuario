package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input;

public interface JWTServicePort {
    public String generateAccessToken(String userId);

    public String generateRefreshToken(String userId);

    public String extractSubject(String token);

    public boolean isAccessTokenValid(String token);

    public boolean isRefreshTokenValid(String token);
}
