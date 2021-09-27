package com.example.kotlin_example_app.article

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.stereotype.Component
import javax.validation.Valid

@Component
class ArticleMutationResolver(private val articleService: ArticleService) : GraphQLMutationResolver {
    fun createNewArticle(@Valid createArticleDto: CreateArticleDto): ArticleEntity =
        articleService.save(createArticleDto)

    fun updateArticleById(articleId: Long, @Valid updateArticleDto: UpdateArticleDto): ArticleEntity? =
        articleService.update(articleId, updateArticleDto).body

    fun deleteArticleById(articleId: Long): Boolean {
        return try {
            articleService.delete(articleId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}