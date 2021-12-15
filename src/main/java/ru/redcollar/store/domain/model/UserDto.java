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
    @Min(6)
    @Max(29)
    private String login;
    @NotNull
    @Min(2)
    @Max(29)
    private String name;
    @NotNull
    @NotEmpty
    @Valid
    private List<RoleDto> roles;
    @NotNull
    @Min(8)
    @Max(10)
    private String password;
}
