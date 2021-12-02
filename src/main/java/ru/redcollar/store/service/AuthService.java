package ru.redcollar.store.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.JwtConverter;
import ru.redcollar.store.domain.entity.Role;
import ru.redcollar.store.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import ru.redcollar.store.domain.model.AuthJwtUser;
import ru.redcollar.store.domain.model.RoleDto;
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public String registerUser(String login, String password, String name)
    {
        if(!userService.existUserByLogin(login)) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(encoder.encode(password));
            user.setRoles(roleService.getDefaultRoles());
            userService.saveUser(user);
            AuthJwtUser userDto = modelMapper.map(user, AuthJwtUser.class);
            return jwtConverter.parseAuthJwtUser(userDto);
        }
        throw new UserExistsException("User " + login + " exist");
    }

    public String loginUser(String login, String password)
    {
        User user = userService.getUserByLogin(login);
        if(user == null || !encoder.matches(password, user.getPassword()))
        {
            throw new BadLoginException("incorrect login or password");
        }
        AuthJwtUser userDto = modelMapper.map(user, AuthJwtUser.class);
        return jwtConverter.parseAuthJwtUser(userDto);
    }

    public AuthJwtUser getUser(String login)
    {
        User user = userService.getUserByLogin(login);
        AuthJwtUser userDto = modelMapper.map(user, AuthJwtUser.class);
        return userDto;
    }
}
