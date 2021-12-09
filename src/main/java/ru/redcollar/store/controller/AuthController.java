package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.JwtTokenUser;
import ru.redcollar.store.domain.model.AuthUser;
import ru.redcollar.store.domain.model.NewUser;
import ru.redcollar.store.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody NewUser user) {
        String key =  authService.registerUser(user.getLogin(), user.getPassword(), user.getName());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/authentication")
    public ResponseEntity<String> authentication(@RequestBody AuthUser user) {
        String key =  authService.loginUser(user.getLogin(), user.getPassword());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/show-me")
    public JwtTokenUser showMe() {
        return authService.getUser();
    }
}
