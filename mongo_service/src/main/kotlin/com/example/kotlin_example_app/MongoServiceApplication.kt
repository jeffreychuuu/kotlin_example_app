package com.example.kotlin_example_app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MongoServiceApplication

// tes
fun main(args: Array<String>) {
    SpringApplication.run(MongoServiceApplication::class.java, *args)
}
