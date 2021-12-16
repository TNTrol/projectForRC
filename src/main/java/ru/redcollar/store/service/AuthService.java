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
import ru.redcollar.store.exceptions.BadLoginException;
import ru.redcollar.store.exceptions.UserExistsException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final RoleService roleService;
    private final UserService userService;
    private final JwtConverter jwtConverter;
    private final PasswordEncoder encoder;
    private final ModelMapper modelMapper;

    public String registerUser(String login, String password, String name) {
        if(!userService.existUserByLogin(login)) {
            User user = new User();
            user.setLogin(login);
            user.setName(name);
            user.setPassword(encoder.encode(password));
            user.setRoles(roleService.getDefaultRoles());
            userService.saveUser(user);
            JwtTokenUser userDto = modelMapper.map(user, JwtTokenUser.class);
            return jwtConverter.parseAuthJwtUser(userDto);
        }
        log.error("User " + login + " exist");
        throw new UserExistsException("User " + login + " exist");
    }

    public String loginUser(String login, String password) {
        User user = userService.getUserByLogin(login);
        if(user == null || !encoder.matches(password, user.getPassword())) {
            log.error("incorrect login or password");
            throw new BadLoginException("incorrect login or password");
        }
        JwtTokenUser userDto = modelMapper.map(user, JwtTokenUser.class);
        return jwtConverter.parseAuthJwtUser(userDto);
    }

    public JwtTokenUser getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByLogin((String) authentication.getCredentials());
        JwtTokenUser userDto = modelMapper.map(user, JwtTokenUser.class);
        return userDto;
    }
}
