package com.example.kotlin_example_app.article.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateArticleDto(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("content")
    val content: String?,
    @JsonProperty("authorId")
    val authorId: String?
)