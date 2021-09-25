package com.example.kotlin_example_app.article

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
@Table(name = "article")
data class Article (
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,

        @get: NotBlank
        val title: String = "",

        @get: NotBlank
        val content: String = ""
)