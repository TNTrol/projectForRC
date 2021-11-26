package service;

import domain.Role;

import java.util.List;

public interface RoleService {

    List<Role> getDefaultRoles();

    void saveRole(Role role);
}
