package com.example.kotlin_example_app

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@Configuration
class AppConfig {
    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(Info().title("RDBMS Service"))


    @Value("\${spring.redis.host}")
    val redisHost: String = ""

    @Value("\${spring.redis.port}")
    val redisPort: Int = 0

    @Value("\${spring.redis.password}")
    val redisPassword: String = ""

    @Bean
    fun jedisConnectionFactory(): JedisConnectionFactory {
        val config: RedisStandaloneConfiguration = RedisStandaloneConfiguration(redisHost, redisPort)
        config.password = RedisPassword.of(redisPassword)
        return JedisConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any>? {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(jedisConnectionFactory())
        return template
    }
}