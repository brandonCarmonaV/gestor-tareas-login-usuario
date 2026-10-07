package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto;

import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.Rol;

public record UserDTO(String id, Rol rol, String email, String name, String pass) {
    public static UserDTO fromDomain(User user) {
        return new UserDTO(user.getId(), user.getRol(), user.getEmail(), user.getName(), user.getPass());
    }
} 
