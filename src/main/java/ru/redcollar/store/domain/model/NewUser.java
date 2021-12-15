package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUser {
    @NotNull
    @Min(6)
    @Max(29)
    private String login;
    @NotNull
    @Min(8)
    @Max(10)
    private String password;
    @NotNull
    @Min(2)
    @Max(29)
    private String name;
}
