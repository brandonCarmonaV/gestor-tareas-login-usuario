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
        return new User(entity.getId(), entity.getName(), entity.getPass());
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setPass(domain.getPass());
        return entity;
    }
}
