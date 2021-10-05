package com.example.mongo_service

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MongoServiceApplication

//test
fun main(args: Array<String>) {
    SpringApplication.run(MongoServiceApplication::class.java, *args)
}
