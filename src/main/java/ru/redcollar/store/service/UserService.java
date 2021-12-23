package ru.redcollar.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.redcollar.store.domain.entity.User;
import ru.redcollar.store.domain.model.RoleDto;
import ru.redcollar.store.domain.model.UserDto;
import ru.redcollar.store.domain.model.UserUpdateDto;
import ru.redcollar.store.exceptions.UserDontExistException;
import ru.redcollar.store.exceptions.UserExistsException;
import ru.redcollar.store.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public boolean existUserByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public boolean existUserByLoginOrEmail(String login, String email) {
        return userRepository.existsByLoginOrEmail(login, email);
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void updateUser(UserUpdateDto userUpdate) {
        User user = userRepository.findByLogin(userUpdate.getLogin());
        if (user == null) {
            log.error("User {} don't exist! ", userUpdate.getLogin());
            throw new UserDontExistException(userUpdate.getLogin());
        }
        User resUser = modelMapper.map(userUpdate, User.class);
        List<Long> roleIds = userUpdate.getRoles().stream()
                .map(RoleDto::getId)
                .collect(Collectors.toList());
        user.setRoles(roleService.getRolesByIds(roleIds));
        resUser.setId(user.getId());
        resUser.setPassword(user.getPassword());
        userRepository.save(resUser);
    }

    public void saveUser(UserDto userDto) {
        if (userRepository.existsByLogin(userDto.getLogin())) {
            log.error("User {} exist!", userDto.getLogin());
            throw new UserExistsException(userDto.getLogin() + " exist!");
        }
        User user = modelMapper.map(userDto, User.class);
        List<Long> roleIds = userDto.getRoles().stream()
                .map(RoleDto::getId)
                .collect(Collectors.toList());
        user.setRoles(roleService.getRolesByIds(roleIds));
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setId(null);
        userRepository.save(user);
    }

    public UserDto getUserDtoByLogin(String login) {
        User user = getUserByLogin(login);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto getUserById(Long id) {
        return modelMapper.map(userRepository.getById(id), UserDto.class);
    }

    public List<UserDto> getAllUsersDto(int page, int size) {
        if (page < 0 || size < 0) {
            return Collections.emptyList();
        }
        Pageable pageable = PageRequest.of(page, size);
        List<Long> ids = userRepository.findAllIdsWithPagination(pageable);
        return userRepository.findAllUser(ids).stream().map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public Long getIdByLogin(String login){
        return userRepository.findIdByLogin(login);
    }
}
