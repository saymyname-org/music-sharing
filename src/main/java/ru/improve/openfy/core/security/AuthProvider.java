package ru.improve.openfy.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.improve.openfy.api.error.ErrorCode;
import ru.improve.openfy.api.error.ServiceException;
import ru.improve.openfy.core.grpc.imp.GrpcAuthClientServiceImp;
import ru.improve.openfy.core.security.AuthToken;
import ru.improve.openfy.core.security.UserPrincipal;
import ru.improve.openfy.util.Mapper;
import ru.improve.skufify.grpc.AuthClientService;

@Component
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationProvider {

    private final GrpcAuthClientServiceImp grpcAuthClientServiceImp;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = (String) authentication.getCredentials();
        AuthClientService.CheckUserResponse checkUserResponse = grpcAuthClientServiceImp.getUserAuthDataFromAuthService(token);
        if (!checkUserResponse.getIsAuth()) {
            throw new ServiceException(ErrorCode.UNAUTHORIZED);
        }

        UserPrincipal userPrincipal = Mapper.createUserPrincipalFromAuthData(checkUserResponse.getUserId(),
                checkUserResponse.getRolesList());
        return new AuthToken(userPrincipal, token);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthToken.class.isAssignableFrom(authentication);
    }
}
