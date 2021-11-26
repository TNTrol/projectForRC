package service.impl;

import domain.Role;
import repository.RoleRepository;
import service.RoleService;

import java.util.Arrays;
import java.util.List;

public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;
    @Override
    public List<Role> getDefaultRoles()
    {
        return Arrays.asList(roleRepository.getByName("User"));
    }

    @Override
    public void saveRole(Role role)
    {
        roleRepository.save(role);
    }
}
