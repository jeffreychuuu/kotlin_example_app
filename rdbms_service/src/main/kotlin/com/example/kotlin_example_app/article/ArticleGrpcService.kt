package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.Article
import com.example.kotlin_example_app.ArticleList
import com.example.kotlin_example_app.ArticleServiceGrpcKt
import com.example.kotlin_example_app.article.entities.ArticleEntity
import com.google.protobuf.Empty
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class ArticleGrpcService(private val articleService: ArticleService) : ArticleServiceGrpcKt.ArticleServiceCoroutineImplBase() {
    override suspend fun findAllArticles(request: Empty): ArticleList {
        var articles: List<ArticleEntity> =  articleService.findAll()

        var articleList : ArrayList<Article> = ArrayList<Article>()
        for (article: ArticleEntity in articles) {
            var article : Article = Article.newBuilder()
                .setId(article.id)
                .setTitle(article.title)
                .setContent(article.content)
                .setAuthorId(article.authorId)
                .build()
            articleList.add(article)
        }
        return ArticleList.newBuilder().addAllArticles(articleList).build()
    }
}