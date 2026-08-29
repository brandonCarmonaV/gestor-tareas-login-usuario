package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.output.IdGeneratorPort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.UuidGeneratorAdapter;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public IdGeneratorPort idGeneratorPort() {
        return new UuidGeneratorAdapter();
    }
}
