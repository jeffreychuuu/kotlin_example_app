package com.example.kotlin_example_app.article

import Article
import ArticleId
import ArticleList
import com.example.kotlin_example_app.article.entities.ArticleEntity
import com.google.protobuf.Empty
import net.devh.boot.grpc.server.service.GrpcService


@GrpcService
class ArticleGrpcService(private val articleService: ArticleService) : ArticleServiceGrpcKt.ArticleServiceCoroutineImplBase() {
    override suspend fun findAllArticles(emptyRequest: Empty): ArticleList {
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

    override suspend fun findOneArticle(articleId: ArticleId): Article {
        var article: ArticleEntity? =  articleService.findById(articleId.id)
        if (article != null) {
            return Article.newBuilder()
                .setId(article.id)
                .setTitle(article.title)
                .setContent(article.content)
                .setAuthorId(article.authorId)
                .build()
        }
        return Article.newBuilder().build()
    }
}