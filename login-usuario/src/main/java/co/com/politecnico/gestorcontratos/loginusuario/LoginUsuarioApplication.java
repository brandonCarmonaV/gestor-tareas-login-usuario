package co.com.politecnico.gestorcontratos.loginusuario;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.UserRmiAdapter;

@SpringBootApplication
public class LoginUsuarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginUsuarioApplication.class, args);
    }

    @Bean
    public UserRmiPort userRmiPort(UserServicePort userService) throws RemoteException {
        return new UserRmiAdapter(userService);
    }

    @Bean
    public ApplicationRunner registerRmi(UserRmiPort userRmiPort) {
        return args -> {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("LoginService", userRmiPort);
            System.out.println("RMI service published: LoginService");
        };
    }
}
