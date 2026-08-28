package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;

public record UserResponse(String id, String name, String pass) {
    public static UserResponse fromDto(UserDTO applicationDto) {
        return new UserResponse(applicationDto.id(), applicationDto.name(), applicationDto.pass());
    }
}
