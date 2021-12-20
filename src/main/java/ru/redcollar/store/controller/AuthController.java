package ru.redcollar.store.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.JwtTokenUser;
import ru.redcollar.store.domain.model.AuthUser;
import ru.redcollar.store.domain.model.NewUser;
import ru.redcollar.store.domain.model.Token;
import ru.redcollar.store.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @Operation(summary = "New user registration")
    @ApiResponse(responseCode = "409", description = "User exist!",  content = @Content)
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class)))
    public ResponseEntity<Token> registration(@Valid @RequestBody NewUser user) {
        Token key =  authService.registerUser(user);
        return ResponseEntity.ok(key);
    }

    @GetMapping("/authentication")
    @Operation(summary = "User authorization")
    @ApiResponse(responseCode = "404", description = "User don't exist!", content = @Content)
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Token.class)))
    public ResponseEntity<Token> authentication(@Valid @RequestBody AuthUser user) {
        Token key =  authService.loginUser(user.getLogin(), user.getPassword());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/show-me")
    @Operation(summary = "Authentication user")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenUser.class)))
    public JwtTokenUser showMe() {
        return authService.getUser();
    }
}
