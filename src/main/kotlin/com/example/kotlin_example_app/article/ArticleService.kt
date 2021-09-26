package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    fun findAll(): List<ArticleEntity> =
            articleRepository.findAll()


    fun save(@Valid articleEntity: ArticleEntity): ArticleEntity =
            articleRepository.save(articleEntity)


    fun findById(articleId: Long): ResponseEntity<ArticleEntity> {
        return articleRepository.findById(articleId).map { article ->
            ResponseEntity.ok(article)
        }.orElse(ResponseEntity.notFound().build())
    }

    fun update(articleId: Long,
                          @Valid updateArticleDto: UpdateArticleDto): ResponseEntity<ArticleEntity> {
        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: ArticleEntity = existingArticle
                    .copy(content = updateArticleDto.content)

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