package com.example.kotlin_example_app.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MongoServiceRestApi {
    @GET("/api/users/{id}")
    fun findUserById(
        @Path("id") id: String
    ): Call<Any>
}