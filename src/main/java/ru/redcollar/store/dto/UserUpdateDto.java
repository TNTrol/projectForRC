package ru.redcollar.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

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
}
