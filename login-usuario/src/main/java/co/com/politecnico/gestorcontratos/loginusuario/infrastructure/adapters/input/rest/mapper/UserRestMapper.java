package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.mapper;

import org.springframework.stereotype.Component;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.CreateUserCommand;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.CreateUserRequest;

@Component
public class UserRestMapper {
    public CreateUserCommand toCommand(CreateUserRequest request) {
        if (request == null) {
            return null;
        }
        return new CreateUserCommand(request.name(), request.pass());
    }
}
