package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.AuthServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.output.persistence.entity.Rol;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GlobalMiddleware implements HandlerInterceptor {

    private final AuthServicePort authService;
    private final List<String> ADMIN_PATHS = Arrays.asList(
            "/api/users");
    private final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/extract",
            "/login",
            "/logout",
            "/signup");

    public GlobalMiddleware(AuthServicePort authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }
        
        if (isExcluded(request.getRequestURI())) {
            return true;
        }

        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("access_token")) {

                    UserDTO user = authService.extractSubject(cookie.getValue());
                    if (authService.isAccessTokenValid(cookie.getValue()) && user != null) {

                        if (ADMIN_PATHS.contains(request.getRequestURI()) && user.rol() != Rol.ROLE_ADMIN) {
                            break;
                        }
                        return true;
                    }
                    break;
                }
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) throws Exception {
    }

    private boolean isExcluded(String uri) {
        return EXCLUDED_PATHS.contains(uri);
    }
}
