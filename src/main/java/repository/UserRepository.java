package repository;

import domain.User;

import java.util.List;

public interface UserRepository {

    User get(Integer id);

    List<User> getAll();

    void update(User user);

    void save(User user);

    void delete(User user);

    User getByLogin(String login);
}
