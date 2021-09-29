package com.example.mongo_service.user

import com.coxautodev.graphql.tools.GraphQLQueryResolver
import com.example.mongo_service.user.documents.UserDocument
import org.springframework.stereotype.Component

@Component
class UserQueryResolver(private val userService: UserService) : GraphQLQueryResolver {
    fun getAllUsers(): List<UserDocument> =
        userService.findAll()

    fun getUserById(userId: String): UserDocument? =
        userService.findById(userId).body
}