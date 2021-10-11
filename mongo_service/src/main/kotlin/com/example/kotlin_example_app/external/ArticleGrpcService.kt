package com.example.kotlin_example_app.external

import Article
import ArticleList
import com.google.protobuf.Empty
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.devh.boot.grpc.client.inject.GrpcClient
import org.springframework.stereotype.Service

@Service
class ArticleGrpcService {
    @GrpcClient("articleGrpcServer")
    private val articleServiceStub: ArticleServiceGrpcKt.ArticleServiceCoroutineStub? = null

    fun getAllArticles(): ArrayList<Any> {
        var result: ArrayList<Any> = ArrayList()
        var articleList: ArticleList = ArticleList.newBuilder().build()
        runBlocking {
            launch {
                articleList =
                    articleServiceStub?.findAllArticles(Empty.newBuilder().build()) ?: ArticleList.newBuilder().build()
            }
        }
        for (index in 0 until articleList.articlesCount) {
            val article: Article = articleList.getArticles(index)
            result.add(object {
                val id = article.id
                val title = article.title
                val content = article.content
                val authorId = article.authorId
            })
        }

        return result
    }

    fun getOneArticle(id: Long): Any {
        var article: Article = Article.newBuilder().build()
        runBlocking {
            launch {
                article =
                    articleServiceStub?.findOneArticle(ArticleId.newBuilder().setId(id).build())!!
            }
        }
        return object {
            val id = article.id
            val title = article.title
            val content = article.content
            val authorId = article.authorId
        }
    }
}