package com.example.kotlin_example_app

import com.google.protobuf.Empty
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class ExternalService {
    @GrpcClient("articleGrpcServer")
    private val articleServiceStub: ArticleServiceGrpcKt.ArticleServiceCoroutineStub? = null

    fun getAllArticlesCount(): Int {
        var result : ArticleList = ArticleList.newBuilder().build()
        runBlocking {
            launch {
                result = articleServiceStub?.findAllArticles(Empty.newBuilder().build()) ?: ArticleList.newBuilder().build()
            }
        }
        return result.articlesCount
    }
}