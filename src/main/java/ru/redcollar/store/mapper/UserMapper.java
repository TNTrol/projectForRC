package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import ru.redcollar.store.entity.User;
import ru.redcollar.store.dto.JwtTokenUserDto;
import ru.redcollar.store.dto.UserDto;
import ru.redcollar.store.dto.UserUpdateDto;

@Mapper(componentModel = "spring")
public interface UserMapper {

    JwtTokenUserDto userToJwtTokenUser(User user);

    User userUpdateDtoToUser(UserUpdateDto user);

    User userDtoToUser(UserDto user);

    UserDto userToUserDto(User user);
}
