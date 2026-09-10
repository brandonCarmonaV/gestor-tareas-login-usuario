package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence;

import java.util.UUID;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.IdGeneratorPort;

public class UuidGeneratorAdapter implements IdGeneratorPort {

    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
    
}
