package co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto;

public record UpdateUserCommand(String name, String email, String pass) {}
