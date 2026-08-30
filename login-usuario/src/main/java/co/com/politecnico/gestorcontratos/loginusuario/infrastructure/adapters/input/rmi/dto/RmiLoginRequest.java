package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto;

import java.io.Serializable;

public class RmiLoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String pass;

    public RmiLoginRequest() {}

    public RmiLoginRequest(String email, String pass) {
        this.email = email;
        this.pass = pass;
    }

    public String getEmail() { return email; }
    public String getPass() { return pass; }
}
