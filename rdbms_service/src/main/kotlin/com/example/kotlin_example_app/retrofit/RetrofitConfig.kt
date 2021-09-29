package com.example.kotlin_example_app.retrofit

import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import retrofit2.converter.jackson.JacksonConverterFactory


@Configuration
class RetrofitConfig {
    @Autowired
    var environment: Environment? = null

    @Bean("mongoServiceRetrofit")
    fun createMongoServiceRetrofit(): Retrofit? {
        return Retrofit.Builder()
            .baseUrl(environment!!.getProperty("external-services.mongo-service.endpoint")!!)
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okhttpclient()!!)
            .build()
    }

    fun okhttpclient(): OkHttpClient? {
        val logInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .addNetworkInterceptor(logInterceptor)
            .build()
    }
}