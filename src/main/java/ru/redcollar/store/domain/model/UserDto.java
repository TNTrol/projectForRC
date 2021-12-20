package ru.redcollar.store.domain.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotNull
    private String login;
    @NotNull
    private String name;
    @NotNull
    @NotEmpty
    @Valid
    private List<RoleDto> roles;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
