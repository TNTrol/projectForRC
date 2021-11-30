package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;

    public void registerUser(String login, String password, String name)
    {
        if(!userService.existUserByLogin(login)) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(password);
            user.setRoles(roleService.getDefaultRoles());
            userService.saveUser(user);
        }
    }

    public void loginUser(String login, String password)
    {
        User user = userService.getUserByLogin(login);
        if(user == null || !user.getPassword().equals(password))
        {
            // тут нужно ругаться что пользователя нет с таким логином и паролем
        }
    }
}
