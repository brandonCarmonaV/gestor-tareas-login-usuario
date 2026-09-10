package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto;

import co.com.politecnico.gestorcontratos.loginusuario.domain.model.User;

public record UserDTO(String id, String email, String name, String pass) {
    public static UserDTO fromDomain(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getPass());
    }
} 
