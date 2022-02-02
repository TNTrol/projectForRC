package ru.redcollar.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.dto.JwtTokenUserDto;
import ru.redcollar.store.dto.CredentialsDto;
import ru.redcollar.store.dto.NewUserDto;
import ru.redcollar.store.dto.TokenDto;
import ru.redcollar.store.service.AuthService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    @Operation(summary = "New user registration")
    @ApiResponse(responseCode = "409", description = "User exist!", content = @Content)
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)))
    public ResponseEntity<TokenDto> registration(@Valid @RequestBody NewUserDto user) {
        TokenDto key = authService.registerUser(user);
        return ResponseEntity.ok(key);
    }

    @GetMapping("/authentication")
    @Operation(summary = "User authorization")
    @ApiResponse(responseCode = "404", description = "User don't exist!", content = @Content)
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)))
    public ResponseEntity<TokenDto> authentication(@Valid @RequestBody CredentialsDto user) {
        TokenDto key = authService.loginUser(user.getLogin(), user.getPassword());
        return ResponseEntity.ok(key);
    }

    @GetMapping("/show-me")
    @Operation(summary = "Authentication user")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtTokenUserDto.class)))
    public JwtTokenUserDto showMe() {
        return authService.getUser();
    }
}
