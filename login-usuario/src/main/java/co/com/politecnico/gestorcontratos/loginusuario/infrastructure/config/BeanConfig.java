package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rmi.RmiServerBootstrap;
import jakarta.annotation.PostConstruct;

@Configuration
@EnableConfigurationProperties(RmiConfig.class)
public class BeanConfig {
    private final RmiServerBootstrap rmiServerBootstrap;
    private final RmiConfig rmiConfig;

    public BeanConfig(RmiServerBootstrap rmiServerBootstrap, RmiConfig rmiConfig) {
        this.rmiServerBootstrap = rmiServerBootstrap;
        this.rmiConfig = rmiConfig;
    }
    /**
    *@throws RuntimeException
    */
     
    @PostConstruct
    public void initRmiServer() {
        try {
            rmiServerBootstrap.start(rmiConfig.registryPort());
            System.out.println("RMI service published");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar servidor RMI", e);
        }
    }
}
