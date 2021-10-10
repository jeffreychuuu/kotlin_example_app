package com.example.kotlin_example_app.user

import com.example.kotlin_example_app.ArticleList
import com.example.kotlin_example_app.ExternalService
import com.example.kotlin_example_app.user.documents.UserDocument
import com.example.kotlin_example_app.user.dto.CreateUserDto
import com.example.kotlin_example_app.user.dto.UpdateUserDto
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "UserRestController")
@RestController
@RequestMapping("/api")
class UserRestController(private val userService: UserService, private val externalService: ExternalService) {
    @GetMapping("/articles/count")
    fun getALlExternals(): Int =
        externalService.getAllArticlesCount()

    @GetMapping("/users")
    fun getAllArticles(): List<UserDocument> =
        userService.findAll()

    @PostMapping("/users")
    fun createNewArticle(@Valid @RequestBody createUserDto: CreateUserDto): UserDocument =
        userService.save(createUserDto)


    @GetMapping("/users/{id}")
    fun getArticleById(@PathVariable(value = "id") userId: String): ResponseEntity<UserDocument> =
        userService.findById(userId);


    @PatchMapping("/users/{id}")
    fun updateArticleById(
        @PathVariable(value = "id") userId: String,
        @Valid @RequestBody updateUserDto: UpdateUserDto
    ): ResponseEntity<UserDocument> =
        userService.update(userId, updateUserDto);

    @DeleteMapping("/users/{id}")
    fun deleteArticleById(@PathVariable(value = "id") userId: String): ResponseEntity<Void> =
        userService.delete(userId);
}