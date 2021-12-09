package ru.redcollar.store.controller;

import lombok.RequiredArgsConstructor;
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
    public void createUser(@RequestBody UserDto user) {
        userService.saveUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody UserUpdateDto user) {
        userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/by-login/{login}")
    public UserDto getUser(@PathVariable String login) {
        return userService.getUserDtoByLogin(login);
    }

    @GetMapping("/by-id/{id}")
    public UserDto getUser(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/list/{page}/{size}")
    public List<UserDto> getAllUsers(@PathVariable int page,@PathVariable int size){
        return userService.getAllUsersDto(page, size);
    }
}
