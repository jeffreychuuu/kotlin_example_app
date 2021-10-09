package com.example.mongo_service.user.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class UpdateUserDto(
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("age")
    val age: Int?,
    @JsonProperty("gender")
    val gender: String?
)