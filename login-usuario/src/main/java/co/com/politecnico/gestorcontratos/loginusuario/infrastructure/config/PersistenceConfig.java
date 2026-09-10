package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.repository")
public class PersistenceConfig {}
