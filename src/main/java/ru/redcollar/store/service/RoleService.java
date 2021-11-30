package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Role;
import ru.redcollar.store.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getDefaultRoles()
    {
        return Arrays.asList(roleRepository.findByName("User"));
    }

    public void saveRole(Role role)
    {
        roleRepository.save(role);
    }
}
