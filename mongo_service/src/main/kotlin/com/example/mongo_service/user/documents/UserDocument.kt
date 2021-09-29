package com.example.mongo_service.user.documents

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "customers")
data class UserDocument(
    @Id
    val id: String = "",

    @Field("name")
    val name: String = "",

    @Field("age")
    val age: Int? = null,

    @Field("gender")
    val gender: String? = null
)