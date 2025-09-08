package co.com.pragma.api.mapper;


import co.com.pragma.api.dto.LoginResponseDto;

import co.com.pragma.model.tokenprovider.TokenProvider;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginMapperDto {

    LoginResponseDto toResponse(TokenProvider tokenProvider);

}
