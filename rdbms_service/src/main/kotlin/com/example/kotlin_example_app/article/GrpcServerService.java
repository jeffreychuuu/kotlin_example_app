package com.example.kotlin_example_app.article;

import demo.GreeterGrpc;
import demo.HelloReply;
import demo.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.InitializingBean;

@GrpcService
public class GrpcServerService extends GreeterGrpc.GreeterImplBase implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("com.example.kotlin_example_app.article.GrpcServerService was constructed!");
    }

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + req.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
