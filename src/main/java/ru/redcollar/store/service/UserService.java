package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.redcollar.store.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User getUserByLogin(String login)
    {
        return userRepository.findByLogin(login);
    }

    public boolean existUserByLogin(String login)
    {
        return userRepository.existsByLogin(login);
    }

    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }

    public boolean existUserByLoginAndPassword() // нужен данный метод вообще???
    {
        return false;
    }
}
