package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.GlobalMiddleware;

@Configuration
public class MiddlewareConfig implements WebMvcConfigurer {
    private final GlobalMiddleware interceptor;

    public MiddlewareConfig(GlobalMiddleware interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**"); 
    }
}
