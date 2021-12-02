package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.component.JwtConverter;
import ru.redcollar.store.domain.model.AuthJwtUser;
import ru.redcollar.store.domain.model.AuthUser;
import ru.redcollar.store.domain.model.RegistrationUser;
import ru.redcollar.store.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationUser user) {
        String key =  authService.registerUser(user.getLogin(), user.getPassword(), user.getName());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/authentication")
    public ResponseEntity<String> authentication(@RequestBody AuthUser user) {
        String key =  authService.loginUser(user.getLogin(), user.getPassword());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/show-me")
    public AuthJwtUser showMe() {
        return authService.getUser();
    }
}
