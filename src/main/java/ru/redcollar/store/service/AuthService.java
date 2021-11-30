package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder encoder;

    public void registerUser(String login, String password, String name)
    {
        if(!userService.existUserByLogin(login)) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(encoder.encode(password));
            user.setRoles(roleService.getDefaultRoles());
            userService.saveUser(user);
        }
    }

    public void loginUser(String login, String password)
    {
        User user = userService.getUserByLogin(login);
        String password1 = user.getPassword();
        String password2 = encoder.encode(password);
        if(user == null || !encoder.matches(password, user.getPassword()))
        {
            // тут нужно ругаться что пользователя нет с таким логином и паролем
        }
    }
}
