package ru.redcollar.store.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenUser {
    private Long id;
    private String login;
    private String name;
    private String email;
    private List<RoleDto> roles;
}
