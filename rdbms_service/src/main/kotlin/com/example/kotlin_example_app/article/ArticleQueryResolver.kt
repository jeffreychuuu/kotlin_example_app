package com.example.kotlin_example_app.article

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.stereotype.Component

@Component
class ArticleQueryResolver(private val articleService: ArticleService) : GraphQLQueryResolver {
    fun getAllArticles(): List<ArticleEntity> =
        articleService.findAll()

    fun getArticleById(articleId: Long): ArticleEntity? =
        articleService.findById(articleId).body
}