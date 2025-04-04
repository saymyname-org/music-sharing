package ru.improve.openfy.core.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;

    private final String jwt;

    public AuthToken(UserPrincipal userPrincipal, String jwt) {
        super(userPrincipal.getAuthorities());
        this.userPrincipal = userPrincipal;
        this.jwt = jwt;
        setAuthenticated(true);
    }

    public AuthToken(String jwt) {
        super(null);
        this.userPrincipal = null;
        this.jwt= jwt;
        setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.jwt;
    }

    @Override
    public Object getPrincipal() {
        return this.userPrincipal;
    }
}
