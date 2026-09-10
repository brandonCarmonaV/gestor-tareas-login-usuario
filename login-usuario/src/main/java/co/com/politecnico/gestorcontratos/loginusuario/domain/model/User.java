package co.com.politecnico.gestorcontratos.loginusuario.domain.model;

public final class User {
    private final String id;
    private final String email;
    private final String name;
    private final String pass;

    public User(String id, String email, String name, String pass) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
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
        return new User(this.id, this.email, name, this.pass);
    }
}
