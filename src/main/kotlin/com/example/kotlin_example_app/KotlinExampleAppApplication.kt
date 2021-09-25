package com.example.kotlin_example_app

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class KotlinExampleAppApplication

fun main(args: Array<String>) {
	SpringApplication.run(KotlinExampleAppApplication::class.java, *args)
}
