package com.example.kotlin_example_app.external

import com.example.kotlin_example_app.retrofit.MongoServiceRestApi
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import retrofit2.Retrofit

@Service
class MongoRestService {
    @Autowired
    @Qualifier("mongoServiceRetrofit")
    lateinit var mongoServiceRetrofit: Retrofit

    fun findById(id: String): Any? {
        val mongoServiceRestApi: MongoServiceRestApi = mongoServiceRetrofit.create(MongoServiceRestApi::class.java)
        val response = mongoServiceRestApi.findUserById(id).execute()
        return response.body()
    }
}