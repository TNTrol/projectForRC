package ru.redcollar.store.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "New user creation")
    public ResponseEntity<Void> createUser(@Parameter(description = "New user") @Valid @RequestBody UserDto user) {
        userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    @Operation(summary = "User update")
    public ResponseEntity<Void> updateUser(@Parameter(description = "Updated user") @Valid @RequestBody UserUpdateDto user) {
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Removing a user")
    public ResponseEntity<Void> deleteUser(@Parameter(description = "Id of user") @PathVariable @Min(1) Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/by-login/{login}")
    @Operation(summary = "Getting user by login")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<UserDto> getUser(@Parameter(description = "Login of user") @PathVariable @Min(6) @Max(29) String login) {
        return ResponseEntity.ok(userService.getUserDtoByLogin(login));
    }

    @GetMapping("/by-id/{id}")
    @Operation(summary = "Getting user by id")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class)))
    public ResponseEntity<UserDto> getUser(@Parameter(description = "Id of user") @PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/list")
    @Operation(summary = "Getting all users with pageable")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserDto.class))))
    public ResponseEntity<List<UserDto>> getAllUsers(@Parameter(description = "Number of page", required = true) @RequestParam(name = "page") @Min(0) int page, @Parameter(description = "Size of page", required = true) @RequestParam(name = "size") @Min(1) int size) {
        return ResponseEntity.ok(userService.getAllUsersDto(page, size));
    }
}
