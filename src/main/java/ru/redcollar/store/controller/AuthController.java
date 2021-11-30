package ru.redcollar.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;
import ru.redcollar.store.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/registration")
    public ResponseEntity registration(@RequestBody String login, @RequestBody String password, @RequestBody String name)
    {
        try {
            authService.registerUser(login, password, name);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (UserExistsException e)
        {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/authentication")
    public ResponseEntity authentication(@RequestBody String login, @RequestBody String password)
    {
        try {
            authService.loginUser(login, password);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (BadLoginException e)
        {
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
        }
    }
}
