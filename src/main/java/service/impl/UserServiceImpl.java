package service.impl;

import domain.User;
import repository.UserRepository;
import service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public void saveUser(User user)
    {
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.getAll();
    }

    @Override
    public User getUserByLogin(String login)
    {
        return userRepository.getByLogin(login);
    }
}
