package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RestController;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.CreateUserRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginRestRmiAdapter {
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody CreateUserRequest request) {
        try {
            String server = "localhost";

            Registry registry = LocateRegistry.getRegistry(server, 1099);
            UserRmiPort userPort = (UserRmiPort) registry.lookup("LoginService");
            return userPort.login(new RmiLoginRequest(request.name(), request.pass()))
                    ? ResponseEntity.accepted().body("User found")
                    : ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User not found");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User not found");
    }
}
