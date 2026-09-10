package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.mapper;

import org.springframework.stereotype.Component;

import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.UserEntity;

@Component
public class UserPersistenceMapper {
    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        return new User(entity.getId(), entity.getEmail(), entity.getName(), entity.getPasswordHash());
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setName(domain.getName());
        entity.setPasswordHash(domain.getPass());
        return entity;
    }
}
