package ru.redcollar.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.Role;
import ru.redcollar.store.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getDefaultRoles()
    {
        return Arrays.asList(roleRepository.findByName("User"));
    }

    public void saveRole(Role role)
    {
        roleRepository.save(role);
    }
}
