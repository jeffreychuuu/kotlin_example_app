package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "ArticleRestController")
@RestController
@RequestMapping("/api")
class ArticleRestController(private val articleService: ArticleService) {

    @GetMapping("/articles")
    fun getAllArticles(): List<ArticleEntity> =
        articleService.findAll()


    @PostMapping("/articles")
    fun createNewArticle(@Valid @RequestBody createArticleDto: CreateArticleDto): ResponseEntity<ArticleEntity> {
        val articleEntity: ArticleEntity? = articleService.save(createArticleDto)
        return if (articleEntity == null)
            ResponseEntity.notFound().build()
        else
            ResponseEntity(articleEntity, HttpStatus.CREATED)
    }


    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<ArticleEntity> {
        val articleEntity: ArticleEntity? = articleService.findById(articleId)
        return if (articleEntity == null)
            ResponseEntity.notFound().build()
        else
            ResponseEntity(articleEntity, HttpStatus.OK)
    }


    @PatchMapping("/articles/{id}")
    fun updateArticleById(
        @PathVariable(value = "id") articleId: Long,
        @Valid @RequestBody updateArticleDto: UpdateArticleDto
    ): ResponseEntity<ArticleEntity> {
        val articleEntity: ArticleEntity? = articleService.update(articleId, updateArticleDto)
        return if (articleEntity == null)
            ResponseEntity.notFound().build()
        else
            ResponseEntity(articleEntity, HttpStatus.OK)
    }

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> {
        val result: Boolean = articleService.delete(articleId)
        return if (result)
            ResponseEntity.ok().build()
        else
            ResponseEntity.notFound().build()
    }
}