package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {
    @NotNull
    @Min(6)
    @Max(29)
    private String login;
    @NotNull
    @Min(8)
    @Max(29)
    private String password;
}
