package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.UserPersistencePort;
import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.UserEntity;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.mapper.UserPersistenceMapper;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.repository.UserRepository;

@Component
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository repository;

    public UserPersistenceAdapter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User save(User user) {
        UserEntity userToSave = UserPersistenceMapper.toEntity(user);
        UserEntity savedEntity = repository.save(userToSave);
        return UserPersistenceMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findById(String id) {
        Optional<UserEntity> entityOptional = repository.findById(id);
        return entityOptional.map(UserPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> entityOptional = repository.findByEmail(email);
        return entityOptional.map(UserPersistenceMapper::toDomain);
    }
    

    @Override
    public List<User> findAll() {
        List<UserEntity> entities = repository.findAll();
        return entities.stream().map(UserPersistenceMapper::toDomain).collect(Collectors.toList());
    }
}
