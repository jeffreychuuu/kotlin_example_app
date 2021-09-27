package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    fun findAll(): List<ArticleEntity> =
            articleRepository.findAll()


    fun save(@Valid createArticleDto: CreateArticleDto): ArticleEntity{
        val mapper = jacksonObjectMapper()
        val articleEntity: ArticleEntity = mapper.convertValue<ArticleEntity>(createArticleDto)
        return articleRepository.save(articleEntity)
    }


    fun findById(articleId: Long): ResponseEntity<ArticleEntity> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    fun update(articleId: Long,
                          @Valid updateArticleDto: UpdateArticleDto): ResponseEntity<ArticleEntity> {
        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: ArticleEntity = existingArticle
                    .copy(title = if (updateArticleDto?.title != null) updateArticleDto.title else existingArticle.title,
                            content = if (updateArticleDto?.content != null) updateArticleDto.content else existingArticle.content)

            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }

    fun delete(articleId: Long): ResponseEntity<Void> {
        return articleRepository.findById(articleId).map { article  ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}