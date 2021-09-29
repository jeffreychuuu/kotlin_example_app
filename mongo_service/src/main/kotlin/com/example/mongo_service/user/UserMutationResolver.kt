package com.example.mongo_service.user

import com.coxautodev.graphql.tools.GraphQLMutationResolver
import com.example.mongo_service.user.documents.UserDocument
import com.example.mongo_service.user.dto.CreateUserDto
import com.example.mongo_service.user.dto.UpdateUserDto
import org.springframework.stereotype.Component
import javax.validation.Valid

@Component
class UserMutationResolver(private val userService: UserService) : GraphQLMutationResolver {
    fun createNewUser(@Valid createUserDto: CreateUserDto): UserDocument =
        userService.save(createUserDto)

    fun updateUserById(userId: String, @Valid updateUserDto: UpdateUserDto): UserDocument? =
        userService.update(userId, updateUserDto).body

    fun deleteUserById(userId: String): Boolean {
        return try {
            userService.delete(userId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}