package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import org.springframework.stereotype.Component;

import rmi.shared.AuthRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.JWTServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;

@Component
public class RmiServerBootstrap {

    private final JWTServicePort jwtService;
    private final UserServicePort userService;

    public RmiServerBootstrap(JWTServicePort jwtService, UserServicePort userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    /**
     * @param registryPort
     * @throws RemoteException
     */
    public void start(int registryPort) throws RemoteException {
        Registry registry = LocateRegistry.createRegistry(registryPort);

        AuthRmiPort authRmiService = new AuthRmiAdapter(userService, jwtService);

        registry.rebind("AuthService", authRmiService);
    }
}
