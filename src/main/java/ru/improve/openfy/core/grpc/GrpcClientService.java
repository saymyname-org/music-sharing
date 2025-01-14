package ru.improve.openfy.core.grpc;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.improve.skufify.grpc.HelloTestGrpc;
import ru.improve.skufify.grpc.HelloTestProto;

@Service
public class GrpcClientService {

    @GrpcClient("myGrpcService")
    private HelloTestGrpc.HelloTestBlockingStub helloTestBlockingStub;

    public String sayHello() {
        HelloTestProto.HelloRequest helloRequest = HelloTestProto.HelloRequest.newBuilder()
                .setName("testname1")
                .build();

        HelloTestProto.HelloReply reply = helloTestBlockingStub.sayHello(helloRequest);
        return reply.getMessage();
    }
}
