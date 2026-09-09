package co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.UserServicePort;
import co.com.politecnico.gestorcontratos.loginusuario.application.ports.input.dto.UserDTO;
import co.com.politecnico.gestorcontratos.loginusuario.domain.exception.UserNotFoundException;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.CreateUserRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.UserResponse;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.dto.UpdateUserRequest;
import co.com.politecnico.gestorcontratos.loginusuario.infrastructure.adapters.input.rest.mapper.UserRestMapper;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/users")
public class UserRestAdapter {
    private final UserServicePort service;
    private final UserRestMapper mapper;

    public UserRestAdapter(UserServicePort service, UserRestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody CreateUserRequest request) {
        var command = mapper.toCommand(request);
        UserDTO userDto = service.createUser(command);
        var response = UserResponse.fromDto(userDto);

        return ResponseEntity.created(URI.create("/api/users/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable String id) {
        UserDTO userDto = service.getById(id);
        return UserResponse.fromDto(userDto);
    }

    @PatchMapping
    public UserResponse update(@CookieValue(name = "access_token", required = false) String token,
            @RequestBody UpdateUserRequest request) {
        UserDTO userDto = service.updateUser(token, mapper.toCommand(request));
        return UserResponse.fromDto(userDto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@CookieValue(name = "access_token", required = false) String token) {
        service.deleteUser(token);
    }

    @GetMapping
    public List<UserResponse> listAll() {
        List<UserDTO> userDTOs = service.listAll();
        return userDTOs.stream().map(UserResponse::fromDto).collect(Collectors.toList());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleUserNotFoundException(UserNotFoundException ex) {
        return ex.getMessage();
    }
}
