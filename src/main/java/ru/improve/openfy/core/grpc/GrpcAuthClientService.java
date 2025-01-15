package ru.improve.openfy.core.grpc;

import ru.improve.skufify.grpc.AuthClientService;

public interface GrpcAuthClientService {

    AuthClientService.CheckUserResponse getUserAuthDataFromAuthService(String token);
}
