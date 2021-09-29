package com.example.kotlin_example_app.article

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class ArticleQueryResolver(private val articleService: ArticleService) : GraphQLQueryResolver {
    fun getAllArticles(): List<ArticleEntity> =
        articleService.findAll()

    fun getArticleById(articleId: Long): ArticleEntity? {
        return articleService.findById(articleId) ?: throw RuntimeException("Article Id does not existed")
    }
}