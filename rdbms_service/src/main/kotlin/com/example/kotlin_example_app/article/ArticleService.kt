package com.example.kotlin_example_app.article

import com.example.kotlin_example_app.article.dto.CreateArticleDto
import com.example.kotlin_example_app.article.dto.UpdateArticleDto
import com.example.kotlin_example_app.article.entities.ArticleEntity
import com.example.kotlin_example_app.external.MongoRestService
import com.example.kotlin_example_app.util.RedisUtil
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import retrofit2.Retrofit
import javax.annotation.Resource
import javax.validation.Valid

@Service
class ArticleService(private val articleRepository: ArticleRepository, private val mongoService: MongoRestService) {
    val key = "article"
    val mapper = jacksonObjectMapper()

    @Value("\${spring.redis.ttl}")
    val ttl: Long = 0;

    @Resource
    private lateinit var redisUtil: RedisUtil

    @Autowired
    @Qualifier("mongoServiceRetrofit")
    lateinit var mongoServiceRetrofit: Retrofit

    fun findAll(): List<ArticleEntity> =
        articleRepository.findAll()

    fun save(@Valid createArticleDto: CreateArticleDto): ArticleEntity? {
        val author = mongoService.findById(createArticleDto.authorId) ?: return null
        val articleEntity: ArticleEntity = mapper.convertValue<ArticleEntity>(createArticleDto)
        val result = articleRepository.save(articleEntity)
        Thread {
            redisUtil.hset(key, articleEntity.id.toString(), mapper.writeValueAsString(result), ttl)
        }.start()
        return result
    }


    fun findById(id: Long): ArticleEntity? {
        var redisResult = redisUtil.hget(key, id.toString())
        return if (redisResult != null) {
            val result = mapper.readValue(redisResult.toString(), ArticleEntity::class.java)
            result
        } else
            articleRepository.findById(id).map { result ->
                redisUtil.hset(key, id.toString(), mapper.writeValueAsString(result), ttl)
                result
            }.orElse(null)
    }

    fun update(
        id: Long,
        @Valid updateArticleDto: UpdateArticleDto
    ): ArticleEntity? {
        if (updateArticleDto.authorId != null)
            updateArticleDto.authorId?.let { mongoService.findById(it) } ?: return null
        redisUtil.hdel(key, id.toString())
        return articleRepository.findById(id).map { existingArticle ->
            val updatedArticle: ArticleEntity = existingArticle
                .copy(
                    title = if (updateArticleDto?.title != null) updateArticleDto.title else existingArticle.title,
                    content = if (updateArticleDto?.content != null) updateArticleDto.content else existingArticle.content
                )

            articleRepository.save(updatedArticle)
        }.orElse(null)
    }

    fun delete(id: Long): Boolean {
        redisUtil.hdel(key, id.toString())
        return articleRepository.findById(id).map { article ->
            articleRepository.delete(article)
            true
        }.orElse(false)
    }
}