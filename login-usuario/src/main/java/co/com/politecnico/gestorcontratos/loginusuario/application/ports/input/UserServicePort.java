package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input;

import java.util.List;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.CreateUserCommand;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;

public interface UserServicePort {
    UserDTO createUser(CreateUserCommand command);
    UserDTO getById(String id);
    UserDTO getByEmail(String email);
    List<UserDTO> listAll();
    boolean matches(String raw, String hashed);
}
