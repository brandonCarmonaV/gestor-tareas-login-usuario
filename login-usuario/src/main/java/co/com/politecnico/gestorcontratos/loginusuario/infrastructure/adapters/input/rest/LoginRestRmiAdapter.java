package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RestController;

import rmi.shared.AuthRmiPort;
import rmi.shared.RmiLoginRequest;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;
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
            AuthRmiPort authPort = (AuthRmiPort) registry.lookup("AuthService");
            Map<String, String> userMap = authPort.auth(new RmiLoginRequest(request.email(), request.pass()));

            if (userMap != null) {
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(userMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", "Access denied"));
    }
}
