package ru.redcollar.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.AuthUser;
import ru.redcollar.store.domain.model.RegistrationUser;
import ru.redcollar.store.service.AuthService;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationUser user)
    {
            authService.registerUser(user.getLogin(), user.getPassword(), user.getName());
            return new ResponseEntity<>("response from registration", HttpStatus.OK);
    }

    @GetMapping("/authentication")
    public ResponseEntity<String> authentication(@RequestBody AuthUser user)
    {
            authService.loginUser(user.getLogin(), user.getPassword());
            return new ResponseEntity<>("response from authentication", HttpStatus.OK);
    }
}
