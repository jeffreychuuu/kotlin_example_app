package com.example.mongo_service

import com.google.protobuf.Empty
import io.grpc.ManagedChannelBuilder
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class ExternalService {
//    val channel = ManagedChannelBuilder.forAddress("localhost", 9090).usePlaintext().build()
//
//    @GrpcClient("articleService")
//    private val articleServiceStub: ArticleServiceGrpcKt.ArticleServiceCoroutineStub =
//        ArticleServiceGrpcKt.ArticleServiceCoroutineStub(channel)

    @GrpcClient("articleService")
    private val articleServiceStub: ArticleServiceGrpcKt.ArticleServiceCoroutineStub? = null

    suspend fun getAllArticles(): ArticleList {
        return articleServiceStub?.findAllArticles(Empty.newBuilder().build()) ?: ArticleList.newBuilder().build()
    }
}