package ru.redcollar.store.service;

import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.redcollar.store.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


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

    public void deleteUser(User user)
    {
        userRepository.delete(user);
    }
}
