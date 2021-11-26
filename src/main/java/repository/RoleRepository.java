package repository;

import domain.Role;

public interface RoleRepository {

    Role get(Integer id);

    void save(Role role);

    void update(Role role);

    void delete(Role role);

    Role getByName(String name);
}
