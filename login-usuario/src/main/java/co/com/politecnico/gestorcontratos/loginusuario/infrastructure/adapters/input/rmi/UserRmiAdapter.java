package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

import org.springframework.stereotype.Component;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.JWTServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;

@Component
public class UserRmiAdapter extends UnicastRemoteObject implements UserRmiPort {

    private final UserServicePort userService;
    private final JWTServicePort jwtService;

    public UserRmiAdapter(UserServicePort userService, JWTServicePort jwtService) throws RemoteException {
        super();
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
}
