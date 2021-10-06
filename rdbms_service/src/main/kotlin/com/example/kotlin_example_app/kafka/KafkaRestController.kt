package com.example.kotlin_example_app.kafka

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "KafkaRestController")
@RestController
@RequestMapping("/api")
class KafkaRestController(private val kafkaService: KafkaService) {
    @GetMapping("kafka/send/{input}")
    fun sendMessage(@PathVariable input: String?) {
        return kafkaService.sendMessage(input)
    }
}