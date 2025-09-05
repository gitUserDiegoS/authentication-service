package co.com.pragma.model.usersession;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserSession {

    private final Long userId;

    private final String email;

    private final String name;
}
