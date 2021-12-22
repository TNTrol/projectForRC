package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.component.JwtConverter;
import ru.redcollar.store.domain.entity.User;
import ru.redcollar.store.domain.model.JwtTokenUser;
import ru.redcollar.store.domain.model.NewUser;
import ru.redcollar.store.domain.model.Token;
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;

import java.util.HashSet;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public Token registerUser(NewUser newUser) {
        String temp = newUser.getLogin();
        if (!userService.existUserByLogin(newUser.getLogin())) {
            temp = newUser.getEmail();
            if(!userService.existsUserByEmail(newUser.getEmail())) {
                User user = new User();
                user.setLogin(newUser.getLogin());
                user.setName(newUser.getName());
                user.setEmail(newUser.getEmail());
                user.setPassword(encoder.encode(newUser.getPassword()));
                user.setRoles(roleService.getDefaultRoles());
                userService.saveUser(user);
                JwtTokenUser userDto = modelMapper.map(user, JwtTokenUser.class);
                return new Token(jwtConverter.parseAuthJwtUser(userDto));
            }
        }
        log.error("User {} exist", temp);
        throw new UserExistsException("User " + temp + " exist");
    }

    public Token loginUser(String login, String password) {
        User user = userService.getUserByLogin(login);
        if (user == null || !encoder.matches(password, user.getPassword())) {
            log.error("Incorrect login({}) or password", login);
            throw new BadLoginException("Incorrect login or password");
        }
        JwtTokenUser userDto = modelMapper.map(user, JwtTokenUser.class);
        return new Token(jwtConverter.parseAuthJwtUser(userDto));
    }

    public JwtTokenUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        return modelMapper.map(user, JwtTokenUser.class);
    }
}
