package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.HelloRequest
import com.example.kotlin_example_app.HelloResponse
import com.example.kotlin_example_app.HelloServiceGrpcKt
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class ArticleGrpcService() : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {
    override suspend fun sayHello(request: HelloRequest): HelloResponse {
        return HelloResponse.newBuilder().setHello("fuck you").build()
    }
}