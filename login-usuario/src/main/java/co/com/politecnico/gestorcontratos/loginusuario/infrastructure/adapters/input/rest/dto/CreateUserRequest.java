package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto;

import jakarta.validation.Valid;

@Valid
public record CreateUserRequest(String id, String name, String pass) {}
