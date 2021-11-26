package service.impl;

import domain.User;
import service.AuthService;
import service.RoleService;
import service.UserService;

public class AuthServiceImpl implements AuthService {

    private RoleService roleService;
    private UserService userService;


    @Override
    public boolean registerUser(String login, String password, String name)
    {
        if(userService.getUserByLogin(login) == null)
        {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(password);
            user.setRoles(roleService.getDefaultRoles());
            userService.saveUser(user);
            return true;
        }
        return false; /// exception?????
    }

    @Override
    public boolean loginUser(String login, String password)
    {
        User user = userService.getUserByLogin(login);
        return user != null && user.getPassword().equals(password);
    }
}
