package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.entities.ArticleEntity
import io.swagger.v3.oas.annotations.tags.Tag
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
    fun createNewArticle(@Valid @RequestBody article: ArticleEntity): ArticleEntity =
            articleService.save(article)


    @GetMapping("/articles/{id}")
    fun getArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<ArticleEntity> =
        articleService.findById(articleId);


    @PatchMapping("/articles/{id}")
    fun updateArticleById(@PathVariable(value = "id") articleId: Long,
                          @Valid @RequestBody newArticle: ArticleEntity): ResponseEntity<ArticleEntity> =
            articleService.update(articleId, newArticle);

    @DeleteMapping("/articles/{id}")
    fun deleteArticleById(@PathVariable(value = "id") articleId: Long): ResponseEntity<Void> =
            articleService.delete(articleId);
}