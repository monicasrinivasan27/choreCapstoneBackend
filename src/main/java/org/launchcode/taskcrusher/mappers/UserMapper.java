package org.launchcode.taskcrusher.mappers;

import org.launchcode.taskcrusher.models.dto.SignUpDto;
import org.launchcode.taskcrusher.models.dto.UserDto;
import org.launchcode.taskcrusher.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);

    @Mapping(target = "password", ignore = true)
    User signUpToUser(SignUpDto signUpDto);
}