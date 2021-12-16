package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.UserDto;
import ru.redcollar.store.domain.model.UserUpdateDto;
import ru.redcollar.store.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserDto user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UserUpdateDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Min(1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-login/{login}")
    public ResponseEntity<UserDto> getUser(@PathVariable @Min(6) @Max(29) String login) {
        return ResponseEntity.ok(userService.getUserDtoByLogin(login));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserDto>  getUser(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(name = "page") @Min(0) int page, @RequestParam(name = "size") @Min(1) int size){
        return ResponseEntity.ok(userService.getAllUsersDto(page, size));
    }
}
