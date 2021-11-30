package ru.redcollar.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.AuthUser;
import ru.redcollar.store.domain.model.RegistrationUser;
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;
import ru.redcollar.store.service.AuthService;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody RegistrationUser user)
    {
        try {
            authService.registerUser(user.getLogin(), user.getPassword(), user.getName());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (UserExistsException e)
        {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/authentication")
    public ResponseEntity authentication(@RequestBody AuthUser user)
    {
        try {
            authService.loginUser(user.getLogin(), user.getPassword());
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (BadLoginException e)
        {
            return ResponseEntity.notFound().build();
        }
    }
}
