package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.Role;
import ru.redcollar.store.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<Role> getRolesByIds(List<Long> ids){
        return ids.stream()
                .map(id -> roleRepository.findById(id).get())
                .collect(Collectors.toList());
    }
}
