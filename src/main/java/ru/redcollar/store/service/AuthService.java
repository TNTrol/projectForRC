package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.JwtConverter;
import ru.redcollar.store.entity.User;
import ru.redcollar.store.dto.JwtTokenUserDto;
import ru.redcollar.store.dto.NewUserDto;
import ru.redcollar.store.dto.TokenDto;
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;
import ru.redcollar.store.mapper.UserMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;

    public TokenDto registerUser(NewUserDto newUser) {
        String temp = newUser.getLogin();
        if (!userService.existUserByLogin(newUser.getLogin())) {
            temp = newUser.getEmail();
            if (!userService.existsUserByEmail(newUser.getEmail())) {
                User user = new User();
                user.setLogin(newUser.getLogin());
                user.setName(newUser.getName());
                user.setEmail(newUser.getEmail());
                user.setPassword(encoder.encode(newUser.getPassword()));
                user.setRoles(roleService.getDefaultRoles());
                userService.saveUser(user);
                JwtTokenUserDto userDto = userMapper.userToJwtTokenUser(user);
                return new TokenDto(jwtConverter.parseAuthJwtUser(userDto));
            }
        }
        log.error("User {} exist", temp);
        throw new UserExistsException("User " + temp + " exist");
    }

    public TokenDto loginUser(String login, String password) {
        User user = userService.getUserByLogin(login);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            log.error("Incorrect login({}) or password", login);
            throw new BadLoginException("Incorrect login or password");
        }
        JwtTokenUserDto userDto = userMapper.userToJwtTokenUser(user);
        return new TokenDto(jwtConverter.parseAuthJwtUser(userDto));
    }

    public JwtTokenUserDto getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        return userMapper.userToJwtTokenUser(user);
    }
}
