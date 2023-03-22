package com.jaygibran.deliveryfood.core.security.authorizationserver;

import java.util.Collection;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class AuthUser extends User {

    private String fullName;
    private Long userId;

    public AuthUser(com.jaygibran.deliveryfood.domain.model.User user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getEmail(), user.getPassword(), authorities);
        fullName = user.getName();
        userId = user.getId();
    }
}
