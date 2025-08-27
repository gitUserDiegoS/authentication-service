package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.CreateUserDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.model.user.User;
import co.com.pragma.model.user.valueObjects.Email;
import co.com.pragma.model.user.valueObjects.LastName;
import co.com.pragma.model.user.valueObjects.Name;
import co.com.pragma.model.user.valueObjects.SalaryBase;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface UserMapperDto {

    default Name mapName(String value) {
        return new Name(value);
    }

    default Email mapEmail(String value) {
        return new Email(value);
    }

    default LastName mapLastName(String value) {
        return new LastName(value);
    }

    default SalaryBase mapSalaryBase(BigDecimal value) {
        return new SalaryBase(value);
    }

    User toModel(CreateUserDto createUserDto);

    UserResponseDto toResponse(User user);

}
