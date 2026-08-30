package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.springframework.stereotype.Component;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;

@Component
public class UserRmiAdapter extends UnicastRemoteObject implements UserRmiPort {

    private final UserServicePort userService;

    public UserRmiAdapter(UserServicePort userService) throws RemoteException {
        super();
        this.userService = userService;
    }

    @Override
    public boolean login(RmiLoginRequest request) throws RemoteException {
        UserDTO userDto = userService.getByEmail(request.getEmail());

        if (userDto == null) {
            return false;
        }
        return userDto.email().equals(request.getEmail())
            && userService.matches(request.getPass(), userDto.pass());
    }
}
