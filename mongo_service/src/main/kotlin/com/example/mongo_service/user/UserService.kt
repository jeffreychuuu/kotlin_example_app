package com.example.mongo_service.user

import com.example.mongo_service.user.dto.CreateUserDto
import com.example.mongo_service.user.dto.UpdateUserDto
import com.example.mongo_service.util.RedisUtil
import com.example.mongo_service.documents.UserRepository
import com.example.mongo_service.user.documents.UserDocument
import com.fasterxml.jackson.module.kotlin.convertValue
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.annotation.Resource
import javax.validation.Valid

@Service
class UserService(private val userRepository: UserRepository) {
    val key = "user"
    val mapper = jacksonObjectMapper()

    @Value("\${spring.redis.ttl}")
    val ttl: Long = 0

    @Resource
    private lateinit var redisUtil: RedisUtil

    fun findAll(): List<UserDocument> =
        userRepository.findAll()

    fun save(@Valid createUserDto: CreateUserDto): UserDocument {
        val userDocument: UserDocument = mapper.convertValue<UserDocument>(createUserDto)
        val result = userRepository.save(userDocument)
        Thread {
            redisUtil.hset(key, userDocument.id, mapper.writeValueAsString(result), ttl)
        }.start()
        return result
    }

    fun findById(userId: String): ResponseEntity<UserDocument> {
        var redisResult = redisUtil.hget(key, userId)
        if (redisResult != null) {
            val result = mapper.readValue(redisResult.toString(), UserDocument::class.java)
            return ResponseEntity.ok(result)
        } else
            return userRepository.findById(userId).map { result ->
                redisUtil.hset(key, userId, mapper.writeValueAsString(result), ttl)
                ResponseEntity.ok(result)
            }.orElse(ResponseEntity.notFound().build())
    }

    fun update(
        userId: String,
        @Valid updateUserDto: UpdateUserDto
    ): ResponseEntity<UserDocument> {
        redisUtil.hdel(key, userId)
        return userRepository.findById(userId).map { existingArticle ->
            val updatedUser: UserDocument = existingArticle
                .copy(
                    name = if (updateUserDto?.name != null) updateUserDto.name else existingArticle.name,
                    age = if (updateUserDto?.age != null) updateUserDto.age else existingArticle.age,
                    gender = if (updateUserDto?.gender != null) updateUserDto.gender else existingArticle.gender
                )

            ResponseEntity.ok().body(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    fun delete(userId: String): ResponseEntity<Void> {
        redisUtil.hdel(key, userId)
        return userRepository.findById(userId).map { article ->
            userRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}