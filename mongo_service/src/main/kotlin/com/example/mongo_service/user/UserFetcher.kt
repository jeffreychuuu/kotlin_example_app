package com.example.mongo_service.user

import com.example.mongo_service.user.documents.UserDocument
import com.example.mongo_service.user.dto.CreateUserDto
import com.example.mongo_service.user.dto.UpdateUserDto
import com.example.netflix_dgs.generated.types.Article
import com.netflix.graphql.dgs.*


@DgsComponent
class UserFetcher(private val userService: UserService) {
    @DgsQuery
    fun getAllUsers(): List<UserDocument> =
        userService.findAll()

    @DgsQuery
    fun getUserById(@InputArgument id: String): UserDocument? =
        userService.findById(id).body

    @DgsMutation
    fun createNewUser(@InputArgument createUserDto: CreateUserDto): UserDocument? =
        userService.save(createUserDto)

    @DgsMutation
    fun updateUserById(@InputArgument id: String, @InputArgument updateUserDto: UpdateUserDto): UserDocument? =
        userService.update(id, updateUserDto).body

    @DgsMutation
    fun deleteUserById(@InputArgument id: String): Boolean {
        return try {
            userService.delete(id)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    @DgsEntityFetcher(name = "Article")
    fun article(values: Map<String?, Any?>): Article? {
        return (values["authorId"] as String?)?.let { Article(it, null) }
    }

    @DgsData(parentType = "Article", field = "author")
    fun authorsFetcher(dataFetchingEnvironment: DgsDataFetchingEnvironment): UserDocument? {
        val article: Article = dataFetchingEnvironment.getSource()
        return userService.findById(article.authorId).body
    }
}