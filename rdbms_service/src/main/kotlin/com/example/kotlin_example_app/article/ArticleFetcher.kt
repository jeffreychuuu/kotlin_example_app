package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ArticleFetcher(private val articleService: ArticleService) {

    @DgsQuery
    fun getAllArticles(): List<ArticleEntity> =
        articleService.findAll()

    @DgsQuery
    fun getArticleById(@InputArgument id: Long): ArticleEntity? {
        return articleService.findById(id) ?: throw RuntimeException("Article Id does not existed")
    }

    @DgsMutation
    fun createNewArticle(@InputArgument createArticleDto: CreateArticleDto): ArticleEntity? {
        return articleService.save(createArticleDto) ?: throw RuntimeException("Author Id does not existed")
    }

    @DgsMutation
    fun updateArticleById(@InputArgument id: Long, @InputArgument updateArticleDto: UpdateArticleDto): ArticleEntity? {
        return articleService.update(id, updateArticleDto)
            ?: throw RuntimeException("Author Id does not existed")
    }

    @DgsMutation
    fun deleteArticleById(@InputArgument id: Long): Boolean {
        return articleService.delete(id)
    }
}