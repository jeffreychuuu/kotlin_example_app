package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long>