package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.redcollar.store.domain.model.UserDto;
import ru.redcollar.store.domain.model.UserUpdateDto;
import ru.redcollar.store.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(@RequestBody UserUpdateDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-login/{login}")
    public ResponseEntity<UserDto> getUser(@PathVariable String login) {
        return ResponseEntity.ok(userService.getUserDtoByLogin(login));
    }

    @GetMapping("/by-id/{id}")
    public ResponseEntity<UserDto>  getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size){
        return ResponseEntity.ok(userService.getAllUsersDto(page, size));
    }
}
