package com.example.mongo_service.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class CreateUserDto(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("age")
    val age: Int,
    @JsonProperty("gender")
    val gender: String
)