package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RestController;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserRmiPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto.RmiLoginRequest;
import jakarta.validation.Valid;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class LoginRestRmiAdapter {

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> auth(@Valid @RequestBody LoginRequest request) {
        try {
            String server = "localhost";

            Registry registry = LocateRegistry.getRegistry(server, 1099);
            UserRmiPort userPort = (UserRmiPort) registry.lookup("LoginService");
            Map<String, String> userMap = userPort.auth(new RmiLoginRequest(request.email(), request.pass()));

            if (userMap != null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", "Access denied"));
    }
}
