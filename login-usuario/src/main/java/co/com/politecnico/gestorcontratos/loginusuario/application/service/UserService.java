package co.com.politecnico.gestorcontratos.loginusuario.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.JWTServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.CreateUserCommand;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UpdateUserCommand;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.IdGeneratorPort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.PasswordHasherPort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserPersistencePort;
import co.com.politecnico.gestorcontratos.loginusuario.domain.exception.UserNotFoundException;
import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.Rol;

@Service
public class UserService implements UserServicePort {

    private final UserPersistencePort persistence;
    private final IdGeneratorPort idGenerator;
    private final PasswordHasherPort passwordHasher;
    private final JWTServicePort jwtService;

    public UserService(UserPersistencePort persistence, IdGeneratorPort idGenerator,
            PasswordHasherPort passwordHasher, JWTServicePort jwtService) {
        this.persistence = persistence;
        this.idGenerator = idGenerator;
        this.passwordHasher = passwordHasher;
        this.jwtService = jwtService;
    }

    @Override
    public UserDTO createUser(CreateUserCommand command) {
        String id = idGenerator.generate();
        String hashedPassword = passwordHasher.hash(command.pass());

        User toSave = new User(id, Rol.ROLE_USER, command.email(), command.name(), hashedPassword);
        User saved = persistence.save(toSave);
        return UserDTO.fromDomain(saved);
    }

    @Override
    public UserDTO getById(String id) {
        User user = persistence.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return UserDTO.fromDomain(user);
    }

    @Override
    public UserDTO getByEmail(String email) {
        User user = persistence.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));
        return UserDTO.fromDomain(user);
    }

    @Override
    public List<UserDTO> listAll() {
        List<User> users = persistence.findAll();
        return users.stream().map(UserDTO::fromDomain).collect(Collectors.toList());
    }

    @Override
    public boolean matches(String raw, String hashed) {
        return passwordHasher.matches(raw, hashed);
    }

    @Override
    public UserDTO updateUser(String token, UpdateUserCommand command) {
        User current = persistence.findById(jwtService.extractSubject(token))
                .orElseThrow(() -> new UserNotFoundException(jwtService.extractSubject(token)));
        String password = command.pass() == null ? current.getPass() : passwordHasher.hash(command.pass());

        User updated = new User(
                current.getId(),
                current.getRol(),
                command.email() == null ? current.getEmail() : command.email(),
                command.name() == null ? current.getName() : command.name(),
                password);

        return UserDTO.fromDomain(persistence.save(updated));
    }

    @Override
    public void deleteUser(String token) {
        String userId = jwtService.extractSubject(token);
        persistence.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        persistence.deleteById(userId);
    }
}
