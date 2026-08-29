package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto;

import jakarta.validation.Valid;

@Valid
public record CreateUserRequest(String name, String pass) {}
