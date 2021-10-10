package com.example.kotlin_example_app.kafka

import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumer {
    @KafkaListener(topics = ["y8p0bb6h-topic"])
    fun receive(payload: String) {
        LOGGER.info("Received payload='$payload'")
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }
}
