package com.example.kotlin_example_app.article.dto

import javax.validation.constraints.NotBlank

class UpdateArticleDto {
    @get: NotBlank
    val content: String = ""
}