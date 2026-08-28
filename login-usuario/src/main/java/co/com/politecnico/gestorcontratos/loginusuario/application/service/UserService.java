package co.com.politecnico.gestorcontratos.loginusuario.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.CreateUserCommand;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserPersistencePort;
import co.com.politecnico.gestorcontratos.loginusuario.domain.exception.UserNotFoundException;
import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;

@Service
public class UserService implements UserServicePort {

    private final UserPersistencePort persistence;

    public UserService(UserPersistencePort persistence) {
        this.persistence = persistence;
    }

    @Override
    public UserDTO createUser(CreateUserCommand command) {
        User toSave = new User(command.id(), command.name(), command.pass());
        User saved = persistence.save(toSave);
        return UserDTO.fromDomain(saved);
    }

    @Override
    public UserDTO getById(String id) {
        User user = persistence.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserDTO.fromDomain(user);
    }

    @Override
    public List<UserDTO> listAll() {
        List<User> users = persistence.findAll();
        return users.stream().map(UserDTO::fromDomain).collect(Collectors.toList());
    }
}
