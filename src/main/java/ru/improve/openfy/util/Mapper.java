package ru.improve.openfy.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ru.improve.openfy.core.security.UserPrincipal;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class Mapper {

    public static UserPrincipal createUserPrincipalFromAuthData(int id, List<String> roles) {
        Set<GrantedAuthority> grantedAuthoritySet = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return UserPrincipal.builder()
                .id(id)
                .authorities(grantedAuthoritySet)
                .build();
    }
}
