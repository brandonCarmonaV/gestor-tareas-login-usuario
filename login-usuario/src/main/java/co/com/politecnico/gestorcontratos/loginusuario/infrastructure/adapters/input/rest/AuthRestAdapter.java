package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.web.bind.annotation.RestController;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.AuthServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.CreateUserRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.LoginRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.UserResponse;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.mapper.UserRestMapper;
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
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class AuthRestAdapter {

    private final AuthServicePort authService;
    private final UserServicePort userService;
    private final UserRestMapper mapper;

    public AuthRestAdapter(AuthServicePort authService, UserServicePort userService, UserRestMapper mapper) {
        this.authService = authService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request) {
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

    @GetMapping("/auth")
    public ResponseEntity<UserResponse> getByCookie(
            @CookieValue(name = "access_token", required = false) String token) {

        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDTO userDto = authService.extractSubject(token);

        if (userDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var response = UserResponse.fromDto(userDto);

        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> create(@RequestBody CreateUserRequest request) {
        var command = mapper.toCommand(request);
        UserDTO userDto = userService.createUser(command);

        return login(new LoginRequest(userDto.email(), request.pass()));
    }

    @GetMapping("/extract")
    public UserDTO extractUser(@RequestParam String token) {
        return authService.extractSubject(token);
    }
}
