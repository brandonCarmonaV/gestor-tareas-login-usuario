package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import rmi.shared.AuthRmiPort;
import rmi.shared.RmiLoginRequest;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.JWTServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;

public class AuthRmiAdapter extends UnicastRemoteObject implements AuthRmiPort {

    private final UserServicePort userService;
    private final JWTServicePort jwtService;

    @Value("${rmi.registry-port}")
    private static int port;

    public AuthRmiAdapter(UserServicePort userService, JWTServicePort jwtService) throws RemoteException {
        super(port);
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    public Map<String, String> auth(RmiLoginRequest request) throws RemoteException {
        UserDTO userDto = userService.getByEmail(request.getEmail());

        if (userDto != null && userDto.email().equals(request.getEmail())
                && userService.matches(request.getPass(), userDto.pass())) {
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
    public String extractSubject(String token) throws RemoteException {
        return jwtService.extractSubject(token);
    }

    @Override
    public boolean isAccessTokenValid(String token) throws RemoteException {
        return jwtService.isAccessTokenValid(token);
    }

    @Override
    public boolean isRefreshTokenValid(String token) throws RemoteException {
        return jwtService.isAccessTokenValid(token);
    }

    @Override
    public String generateRefreshToken(String userId) throws RemoteException {
        return jwtService.generateRefreshToken(userId);
    }
}
