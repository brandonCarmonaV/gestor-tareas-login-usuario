package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RestController;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.AuthServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.Map;

import java.time.Duration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class LoginRestAdapter {

    private final AuthServicePort authService;

    public LoginRestAdapter(AuthServicePort authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> auth(@Valid @RequestBody LoginRequest request) {
        try {
            Map<String, String> userMap = authService.auth(new LoginRequest(request.email(), request.pass()));
            if (userMap != null) {
                ResponseCookie accessCookie = ResponseCookie.from(
                        "access_token",
                        userMap.get("accessToken"))
                        .httpOnly(true)
                        .secure(false)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ofHours(5))
                        .build();

                ResponseCookie refreshCookie = ResponseCookie.from(
                        "refresh_token",
                        userMap.get("refreshToken"))
                        .httpOnly(true)
                        .secure(false)
                        .sameSite("Lax")
                        .path("/")
                        .maxAge(Duration.ofDays(7))
                        .build();

                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                        .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                        .body(Map.of("message", "Authenticated"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("message", "Access denied"));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> deleteCookies(
            HttpServletRequest request,
            HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                Cookie cookieDel = new Cookie(cookie.getName(), "");
                cookieDel.setPath("/");
                cookieDel.setMaxAge(0);
                response.addCookie(cookieDel);
            }
        }

        return ResponseEntity.ok("Cookies deleted.");
    }

    @GetMapping("/extract")
    public Map<String,String> extractUser(@RequestParam String token) {
        return authService.extractSubject(token);
    }
}
