package com.example.kotlin_example_app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.annotation.KafkaListener


@SpringBootApplication
class KotlinExampleAppApplication

@KafkaListener(id = "webGroup", topics = ["y8p0bb6h-topic"])
fun listen(input: String?) {
    println("input value: $input")
}

// test
fun main(args: Array<String>) {
    SpringApplication.run(KotlinExampleAppApplication::class.java, *args)
}
