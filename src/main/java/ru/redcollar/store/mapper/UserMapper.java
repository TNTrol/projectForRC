package ru.redcollar.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.redcollar.store.domain.entity.User;
import ru.redcollar.store.domain.model.JwtTokenUser;
import ru.redcollar.store.domain.model.UserDto;
import ru.redcollar.store.domain.model.UserUpdateDto;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

    JwtTokenUser userToJwtTokenUser(User user);

    User userUpdateDtoToUser(UserUpdateDto user);

    User userDtoToUser(UserDto user);

    UserDto userToUserDto(User user);
}
