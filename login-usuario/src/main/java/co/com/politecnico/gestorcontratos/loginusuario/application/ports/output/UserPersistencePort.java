package co.com.politecnico.gestorcontratos.loginusuario.application.ports.output;

import java.util.List;
import java.util.Optional;

import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;

public interface UserPersistencePort {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
} 
