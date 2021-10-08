package com.example.mongo_service.user

import com.example.mongo_service.user.documents.UserDocument
import graphql.kickstart.tools.GraphQLQueryResolver
import org.springframework.stereotype.Component

@Component
class UserQueryResolver(private val userService: UserService) : GraphQLQueryResolver {
    fun getAllUsers(): List<UserDocument> =
        userService.findAll()

    fun getUserById(userId: String): UserDocument? =
        userService.findById(userId).body
}