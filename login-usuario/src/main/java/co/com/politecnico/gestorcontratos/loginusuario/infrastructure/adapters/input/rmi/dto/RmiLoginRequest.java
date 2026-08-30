package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.dto;

import java.io.Serializable;

public class RmiLoginRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String pass;

    public RmiLoginRequest() {}

    public RmiLoginRequest(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() { return name; }
    public String getPass() { return pass; }
}
