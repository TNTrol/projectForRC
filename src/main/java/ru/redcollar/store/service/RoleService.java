package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.redcollar.store.entity.Role;
import ru.redcollar.store.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getDefaultRoles() {
        return List.of(roleRepository.findByName("User"));
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> getRolesByIds(List<Long> ids) {
        return roleRepository.findByIds(ids);
    }
}
