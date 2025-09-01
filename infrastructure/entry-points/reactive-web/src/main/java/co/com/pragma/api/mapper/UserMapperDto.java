package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.model.user.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDto {

    User toModel(CreateUserDto createUserDto);

    UserResponseDto toResponse(User user);

}
