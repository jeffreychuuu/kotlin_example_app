package com.example.mongo_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories
class MongoServiceApplication

fun main(args: Array<String>) {
    runApplication<MongoServiceApplication>(*args)
}
