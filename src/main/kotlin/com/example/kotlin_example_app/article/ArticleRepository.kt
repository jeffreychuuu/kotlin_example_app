package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.entities.ArticleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by rajeevkumarsingh on 04/10/17.
 */

@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long>