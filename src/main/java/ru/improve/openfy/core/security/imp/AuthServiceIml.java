package ru.improve.openfy.core.security.imp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.security.AuthService;
import ru.improve.openfy.core.security.AuthToken;

@RequiredArgsConstructor
@Service
@Slf4j
public class AuthServiceIml implements AuthService {

    private final AuthenticationManager authenticationManager;

    @Override
    public boolean setAuthentication(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            AuthToken authToken = new AuthToken(token);

            try {
                Authentication authentication = authenticationManager.authenticate(authToken);
                if (authentication.isAuthenticated()) {
                    securityContext.setAuthentication(authentication);
                    return true;
                }
            } catch (ServiceException ex) {
                securityContext.setAuthentication(null);
                SecurityContextHolder.clearContext();
                response.reset();
                throw new ServiceException(ErrorCode.UNAUTHORIZED, ex);
            }
        }

        securityContext.setAuthentication(null);
        SecurityContextHolder.clearContext();
        response.reset();
        throw new ServiceException(ErrorCode.UNAUTHORIZED);
    }
}
