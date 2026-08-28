package co.com.politecnico.gestorcontratos.loginusuario.domain.model;

public final class User {
    private final String id;
    private final String name;
    private final String pass;
    
    public User(String id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public String getId() {
        return id;
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
        return new User(this.id, name, this.pass);
    }
}
