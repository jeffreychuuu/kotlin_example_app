package com.example.kotlin_example_app.kafka

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PathVariable

@Service
class KafkaService(val kafkaTemplate: KafkaTemplate<String, String>) {
    @Autowired
    var environment: Environment? = null

    fun sendMessage(@PathVariable input: String?) {
        try {
            kafkaTemplate.send(environment!!.getProperty("spring.kafka.template.default-topic")!!, input)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}