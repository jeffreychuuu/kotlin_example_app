package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import graphql.kickstart.tools.GraphQLMutationResolver
import org.springframework.stereotype.Component
import java.lang.RuntimeException
import javax.validation.Valid

@Component
class ArticleMutationResolver(private val articleService: ArticleService) : GraphQLMutationResolver {
    fun createNewArticle(@Valid createArticleDto: CreateArticleDto): ArticleEntity? {
        return articleService.save(createArticleDto) ?: throw RuntimeException("Author Id does not existed")
    }

    fun updateArticleById(articleId: Long, @Valid updateArticleDto: UpdateArticleDto): ArticleEntity? {
        return articleService.update(articleId, updateArticleDto)
            ?: throw RuntimeException("Author Id does not existed")
    }

    fun deleteArticleById(articleId: Long): Boolean {
        return articleService.delete(articleId)
    }
}