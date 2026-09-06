package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rmi")
public record RmiConfig(int registryPort) {
}