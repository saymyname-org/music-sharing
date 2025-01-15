package ru.improve.openfy.core.grpc.imp;

import net.devh.boot.grpc.client.inject.GrpcClient;
import ru.improve.openfy.core.grpc.GrpcAuthClientService;
import ru.improve.skufify.grpc.AuthClientGrpc;
import ru.improve.skufify.grpc.AuthClientService;

public class GrpcAuthClientServiceImp implements GrpcAuthClientService {

    @GrpcClient("AuthClientGrpcService")
    private AuthClientGrpc.AuthClientBlockingStub authClientBlockingStub;

    @Override
    public AuthClientService.CheckUserResponse getUserAuthDataFromAuthService(String token) {
        AuthClientService.CheckUserRequest checkUserRequest = AuthClientService.CheckUserRequest.newBuilder()
                .setToken(token)
                .build();

        return authClientBlockingStub.checkUserByToken(checkUserRequest);
    }
}