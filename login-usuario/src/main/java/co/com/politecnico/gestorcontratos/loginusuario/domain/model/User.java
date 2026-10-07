package co.com.politecnico.gestorcontratos.loginusuario.domain.model;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.Rol;

public final class User {
    private final String id;
    private final String email;
    private final String name;
    private final String pass;
    private final Rol rol;

    public User(String id, Rol rol, String email, String name, String pass) {
        this.id = id;
        this.rol = rol;
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
    }

    public Rol getRol() {
        return rol;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public User setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name rejected");
        }
        return new User(this.id, this.rol, this.email, name, this.pass);
    }
}
