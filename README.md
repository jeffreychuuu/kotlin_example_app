# Kotlin Example App

This is a simple guideline on the project creation and scaffolding project based on the recommended practices proposed by NestJS. The following tools will be used on this example:

- [Spring Boot](https://spring.io/projects/spring-boot) - Application Framework for Java, Kotlin & Groovy
- [OpenAPI 3](https://www.openapis.org/) - Specification and standardized documentation for REST API
- [REST API](https://restfulapi.net/) - Acronym for **RE**presentational **S**tate **T**ransfer. It is architectural style for **distributed hypermedia systems**
- [Spring Data](https://spring.io/projects/spring-data) - A familiar and consistent, Spring-based programming model for data access while still retaining the special traits of the underlying data store
- [Spring Validation](https://spring.io/guides/gs/validating-form-input/) - Takes user input and checks the input by using standard *validation* annotations
- [PostgreSQL](https://www.postgresql.org/) - A powerful, open source object-relational database system
- [MongoDB](https://www.mongodb.com/) - A general purpose, document-based, distributed database
- [Redis](https://redis.io/) - An open source (BSD licensed), in-memory data structure store, used as a database, cache, and message broker
- [GraphQL](https://graphql.org/) - A data query and manipulation language for APIs
- [gRPC](https://grpc.io/) - A modern open source high performance Remote Procedure Call (RPC) framework that can run in any environment

## Content

- [Get Started](#Get-Started)
- [Adding Rest Controller](#Adding-Rest-Controller)
- [Adding SpringDoc OpenAPI3 Support](#Adding-SpringDoc-OpenAPI3-Support)
- [Adding Graphql Support](#Adding-Graphql-Support)
- [Adding Spring Data Support](#Adding-Spring-Data-Support)
- [Adding Redis Support](#Adding-Redis-Support)
- [Adding gRPC Support](#Adding-gRPC-Support)

## Get Started

Visit [Spring Initializer](https://start.spring.io/), to init the spring boot application with Kotlin.

## Adding Rest Controller

```kotlin
@RestController
@RequestMapping("/api")
class ArticleRestController(private val articleService: ArticleService) {

    @GetMapping("/articles")
    fun getAllArticles(): List<ArticleEntity> =
            articleService.findAll()
}
```



## Adding SpringDoc OpenAPI3 Support

Add the package to `build.gradle.kts` 

```yaml
implementation("org.springdoc:springdoc-openapi-ui:1.5.2")
```

Add the Spring Doc config to `application.yml`

```yaml
## Spring Doc
springdoc:
  api-docs:
    path: /api-docs
  swagger-ui:
    enabled: true
    path: /swagger-ui-custom.html
    operationsSorter: method
```

Define a configuation class `AppConfig.kt` to customize the bean of OpenApi Spec

```kotlin
@Configuration
class AppConfig {
    @Bean
    fun customOpenAPI(): OpenAPI =
        OpenAPI()
            .components(Components())
            .info(Info().title("Kotlin Example App"))
}
```

Define the Rest Controller Tag

```kotlin
@Tag(name = "ArticleRestController")
class ArticleRestController(private val articleService: ArticleService) {
	...
}
```

Visit the OpenAPI ui by

```
http://localhost:8080/swagger-ui-custom.html
```

Visit the api doc by

```
http://localhost:8080/api-docs
```



## Adding Graphql Support

Add the package to `build.gradle.kts` 

```yaml
implementation("com.graphql-java:graphql-spring-boot-starter:5.0.2")
implementation("com.graphql-java:graphiql-spring-boot-starter:5.0.2")
implementation("com.graphql-java:graphql-java-tools:5.2.4")
```

Add the graphql config to `application.yml`

```yaml
##Graphql
graphql:
  servlet:
    mapping: /graphql

##Grpahiql
graphiql:
  mapping: /graphiql
  endpoint: /graphql
```

Define the graphql schema and place it into `/src/main/resources` as `xxx.graphqls`

```
type Article {
    id: ID!
    title: String!
    content: String!
}

input CreateArticle {
    title: String!
    content: String!
}

input UpdateArticle {
    title: String
    content: String
}

type Query {
    getAllArticles: [Article]
    getArticleById(articleId: ID!): Article
}

type Mutation {
    createNewArticle(articleInput: CreateArticle!) : Article!
    updateArticleById(id: ID!, articleInput: UpdateArticle!) : Article!
    deleteArticleById(id:ID!) : Boolean
}
```

Add the Graphql Query Resolver

```kotlin
@Component
class ArticleQueryResolver(private val articleService: ArticleService) : GraphQLQueryResolver {
    fun getAllArticles(): List<ArticleEntity> =
        articleService.findAll()

    fun getArticleById(articleId: Long): ArticleEntity? =
        articleService.findById(articleId).body
}
```

Add the Graphql Mutation Resolver

```kotlin
@Component
class ArticleMutationResolver(private val articleService: ArticleService) : GraphQLMutationResolver {
    fun createNewArticle(@Valid createArticleDto: CreateArticleDto): ArticleEntity =
        articleService.save(createArticleDto)

    fun updateArticleById(articleId: Long, @Valid updateArticleDto: UpdateArticleDto): ArticleEntity? =
        articleService.update(articleId, updateArticleDto).body

    fun deleteArticleById(articleId: Long): Boolean {
        return try {
            articleService.delete(articleId)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
```

Visit the Grpahiql by

```
http://localhost:8080/graphiql
```

Calling the grpahql api by

```
http://localhost:8080/graphql
```

## Adding Spring Data Support

Add the package to `build.gradle.kts` 

```yaml
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
```

Add the spring data and jpa config to `application.yml`

```yaml
## Spring DATASOURCE (DataSourceAutoConfiguration & DataSourceProperties)
spring:
  datasource:
    hikari:
      connectionTimeout: 20000
      maximumPoolSize: 5
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/postgres
    username: postgres
    password: password
  ## Hibernate Properties
  jpa:
    generate-ddl: true
    ddl-auto: true
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.PostgreSQL81Dialect
```

Create a JpaRepository

```kotlin
@Repository
interface ArticleRepository : JpaRepository<ArticleEntity, Long>
```

Inject the JpaRepository to the way to use

```kotlin
@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    fun findAll(): List<ArticleEntity> =
        articleRepository.findAll()
}
```

JpaRepository has the below default methods

```kotlin
articleRepository.findAll()
articleRepository.save(object)
articleRepository.findById(id)
articleRepository.delete(object)
articleRepository.deleteById(id)
...
```

## Adding Redis Support

Add the package to `build.gradle.kts` 

```yaml
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.5.5")
    implementation("redis.clients:jedis:3.7.0")
```

Add the Spring Doc config to `application.yml`

```yaml
spring:
  ## Redis
  redis:
    host: 127.0.0.1
    port: 6379
    password: password
    ttl: 3600
    tls: false
    database: 0
    pool:
      max-active: 8 # 連接池最大連接數（使用負值表示沒有限制）
      max-wait: -1 # 連接池最大阻塞等待時間（使用負值表示沒有限制）
      max-idle: 8 # 連接池中的最大空閒連接
      min-idle: 0 # 連接池中的最小空閒連接
      timeout: 0 # 連接超時時間（毫秒）
```

Define a `RedisUtil`

```kotlin
@Component
class RedisUtil {

    @Resource
    private lateinit var redisTemplate: RedisTemplate<String, Any>

    // =============================common============================
    /**
     * 指定緩存失效時間
     * @param key 鍵
     * @param time 時間(秒)
     * @return
     */
    fun expire(key: String, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根據key 獲取過期時間
     * @param key 鍵 不能為null
     * @return 時間(秒) 返回0代表為永久有效
     */
    fun getExpire(key: String): Long {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS)
    }

    /**
     * 判斷key是否存在
     * @param key 鍵
     * @return true 存在 false不存在
     */
    fun hasKey(key: String): Boolean {
        try {
            return redisTemplate.hasKey(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 刪除緩存
     * @param key 可以傳一個值 或多個
     */
    fun del(vararg key: String) {
        if (key.isNotEmpty()) {
            if (key.size == 1) {
                redisTemplate.delete(key[0])
            } else {
                redisTemplate.delete(key.toList())
            }
        }
    }

    // ============================String=============================
    /**
     * 普通緩存獲取
     * @param key 鍵
     * @return 值
     */
    operator fun get(key: String?): Any? {
        return if (key == null) null else redisTemplate.opsForValue().get(key)
    }

    /**
     * 普通緩存放入
     * @param key 鍵
     * @param value 值
     * @return true成功 false失敗
     */
    operator fun set(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForValue().set(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 普通緩存放入並設置時間
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒) time要大於0 如果time小於等於0 將設置無限期
     * @return true成功 false 失敗
     */
    operator fun set(key: String, value: Any, time: Long): Boolean {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS)
            } else {
                set(key, value)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 遞增
     * @param key 鍵
     * @param delta 要增加幾(大於0)
     * @return
     */
    fun incr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("遞增因子必須大於0")
        }
        return redisTemplate.opsForValue().increment(key, delta)!!
    }

    /**
     * 遞減
     * @param key 鍵
     * @param delta 要減少幾(小於0)
     * @return
     */
    fun decr(key: String, delta: Long): Long {
        if (delta < 0) {
            throw RuntimeException("遞減因子必須大於0")
        }
        return redisTemplate.opsForValue().increment(key, -delta)!!
    }

    // ================================Map=================================
    /**
     * HashGet
     * @param key 鍵 不能為null
     * @param item 項 不能為null
     * @return 值
     */
    fun hget(key: String, item: String): Any? {
        return redisTemplate.opsForHash<Any, Any>().get(key, item)
    }

    /**
     * 獲取hashKey對應的所有鍵值
     * @param key 鍵
     * @return 對應的多個鍵值
     */
    fun hmget(key: String): Map<Any, Any> {
        return redisTemplate.opsForHash<Any, Any>().entries(key)
    }

    /**
     * HashSet
     * @param key 鍵
     * @param map 對應多個鍵值
     * @return true 成功 false 失敗
     */
    fun hmset(key: String, map: Map<String, Any>): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * HashSet 並設置時間
     * @param key 鍵
     * @param map 對應多個鍵值
     * @param time 時間(秒)
     * @return true成功 false失敗
     */
    fun hmset(key: String, map: Map<String, Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().putAll(key, map)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一張hash表中放入數據,如果不存在將創建
     * @param key 鍵
     * @param item 項
     * @param value 值
     * @return true 成功 false失敗
     */
    fun hset(key: String, item: String, value: Any): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 向一張hash表中放入數據,如果不存在將創建
     * @param key 鍵
     * @param item 項
     * @param value 值
     * @param time 時間(秒) 注意:如果已存在的hash表有時間,這裡將會替換原有的時間
     * @return true 成功 false失敗
     */
    fun hset(key: String, item: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForHash<Any, Any>().put(key, item, value)
            if (time > 0) {
                expire(key, time)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 刪除hash表中的值
     * @param key 鍵 不能為null
     * @param item 項 可以使多個 不能為null
     */
    fun hdel(key: String, vararg item: Any) {
        redisTemplate.opsForHash<Any, Any>().delete(key, *item)
    }

    /**
     * 判斷hash表中是否有該項的值
     * @param key 鍵 不能為null
     * @param item 項 不能為null
     * @return true 存在 false不存在
     */
    fun hHasKey(key: String, item: String): Boolean {
        return redisTemplate.opsForHash<Any, Any>().hasKey(key, item)
    }

    /**
     * hash遞增 如果不存在,就會創建一個 並把新增後的值返回
     * @param key 鍵
     * @param item 項
     * @param by 要增加幾(大於0)
     * @return
     */
    fun hincr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, by)
    }

    /**
     * hash遞減
     * @param key 鍵
     * @param item 項
     * @param by 要減少記(小於0)
     * @return
     */
    fun hdecr(key: String, item: String, by: Double): Double {
        return redisTemplate.opsForHash<Any, Any>().increment(key, item, -by)
    }

    // ============================set=============================
    /**
     * 根據key獲取Set中的所有值
     * @param key 鍵
     * @return
     */
    fun sGet(key: String): Set<Any>? {
        try {
            return redisTemplate.opsForSet().members(key)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 根據value從一個set中查詢,是否存在
     * @param key 鍵
     * @param value 值
     * @return true 存在 false不存在
     */
    fun sHasKey(key: String, value: Any): Boolean {
        try {
            return redisTemplate.opsForSet().isMember(key, value)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將數據放入set緩存
     * @param key 鍵
     * @param values 值 可以是多個
     * @return 成功個數
     */
    fun sSet(key: String, vararg values: Any): Long {
        try {
            return redisTemplate.opsForSet().add(key, *values)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 將set數據放入緩存
     * @param key 鍵
     * @param time 時間(秒)
     * @param values 值 可以是多個
     * @return 成功個數
     */
    fun sSetAndTime(key: String, time: Long, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().add(key, *values)
            if (time > 0)
                expire(key, time)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 獲取set緩存的長度
     * @param key 鍵
     * @return
     */
    fun sGetSetSize(key: String): Long {
        try {
            return redisTemplate.opsForSet().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 移除值為value的
     * @param key 鍵
     * @param values 值 可以是多個
     * @return 移除的個數
     */
    fun setRemove(key: String, vararg values: Any): Long {
        try {
            val count = redisTemplate.opsForSet().remove(key, *values)
            return count!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }
    // ===============================list=================================

    /**
     * 獲取list緩存的內容
     * @param key 鍵
     * @param start 開始
     * @param end 結束 0 到 -1代表所有值
     * @return
     */
    fun lGet(key: String, start: Long, end: Long): List<Any>? {
        try {
            return redisTemplate.opsForList().range(key, start, end)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 獲取list緩存的長度
     * @param key 鍵
     * @return
     */
    fun lGetListSize(key: String): Long {
        try {
            return redisTemplate.opsForList().size(key)!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }

    /**
     * 通過索引 獲取list中的值
     * @param key 鍵
     * @param index 索引 index>=0時， 0 表頭，1 第二個元素，依次類推；index<0時，-1，表尾，-2倒數第二個元素，依次類推
     * @return
     */
    fun lGetIndex(key: String, index: Long): Any? {
        try {
            return redisTemplate.opsForList().index(key, index)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: Any): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: Any, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPush(key, value)
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 將list放入緩存
     *
     * @param key 鍵
     * @param value 值
     * @param time 時間(秒)
     * @return
     */
    fun lSet(key: String, value: List<Any>, time: Long): Boolean {
        try {
            redisTemplate.opsForList().rightPushAll(key, *value.toTypedArray())
            if (time > 0)
                expire(key, time)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 根據索引修改list中的某條數據
     * @param key 鍵
     * @param index 索引
     * @param value 值
     * @return
     */
    fun lUpdateIndex(key: String, index: Long, value: Any): Boolean {
        try {
            redisTemplate.opsForList().set(key, index, value)
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    /**
     * 移除N個值為value
     * @param key 鍵
     * @param count 移除多少個
     * @param value 值
     * @return 移除的個數
     */
    fun lRemove(key: String, count: Long, value: Any): Long {
        try {
            val remove = redisTemplate.opsForList().remove(key, count, value)
            return remove!!
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        }

    }
}
```

Use the RedisUtil

```kotlin
@Service
class ArticleService(private val articleRepository: ArticleRepository) {
    val key = "article"
    val mapper = jacksonObjectMapper()

    @Value("\${spring.redis.ttl}")
    val ttl: Long = 0;

    @Resource
    private lateinit var redisUtil: RedisUtil

    fun findAll(): List<ArticleEntity> =
        articleRepository.findAll()


    fun save(@Valid createArticleDto: CreateArticleDto): ArticleEntity {
        val articleEntity: ArticleEntity = mapper.convertValue<ArticleEntity>(createArticleDto)
        val result = articleRepository.save(articleEntity)
        Thread {
            redisUtil.hset(key, articleEntity.id.toString(), mapper.writeValueAsString(result), ttl)
        }.start()
        return result
    }


    fun findById(articleId: Long): ResponseEntity<ArticleEntity> {
        var redisResult = redisUtil.hget(key, articleId.toString())
        if (redisResult != null) {
            val result = mapper.readValue(redisResult.toString(), ArticleEntity::class.java)
            return ResponseEntity.ok(result)
        } else
            return articleRepository.findById(articleId).map { result ->
                redisUtil.hset(key, articleId.toString(), mapper.writeValueAsString(result), ttl)
                ResponseEntity.ok(result)
            }.orElse(ResponseEntity.notFound().build())
    }

    fun update(
        articleId: Long,
        @Valid updateArticleDto: UpdateArticleDto
    ): ResponseEntity<ArticleEntity> {
        redisUtil.hdel(key, articleId.toString())
        return articleRepository.findById(articleId).map { existingArticle ->
            val updatedArticle: ArticleEntity = existingArticle
                .copy(
                    title = if (updateArticleDto?.title != null) updateArticleDto.title else existingArticle.title,
                    content = if (updateArticleDto?.content != null) updateArticleDto.content else existingArticle.content
                )

            ResponseEntity.ok().body(articleRepository.save(updatedArticle))
        }.orElse(ResponseEntity.notFound().build())
    }

    fun delete(articleId: Long): ResponseEntity<Void> {
        redisUtil.hdel(key, articleId.toString())
        return articleRepository.findById(articleId).map { article ->
            articleRepository.delete(article)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}
```



## Adding gRPC Support

Create a new project for grc_lib

Define the `build.gradle.kts`

```yml
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*;
buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	}
}
plugins {
	id("com.google.protobuf") version "0.8.13"
	kotlin("jvm") version "1.4.10"
}

group = "com.kotlingrpc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
		google()
	}
}


dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("io.grpc:grpc-protobuf:1.33.1")
	implementation("io.grpc:grpc-stub:1.33.1")
	implementation("io.grpc:grpc-netty:1.33.1")
	compileOnly("javax.annotation:javax.annotation-api:1.3.2")
	api("com.google.protobuf:protobuf-java-util:3.13.0")
	implementation("io.grpc:grpc-all:1.33.1")
	api("io.grpc:grpc-kotlin-stub:0.2.1")
	implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
	implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

protobuf {
	protoc{
		artifact = "com.google.protobuf:protoc:3.10.1"
	}
	generatedFilesBaseDir = "$projectDir/src/main/grpc/com.kotlingrpc/generated"
	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
				id("grpckt")
			}
		}
	}
}
```

Create a proto

```proto
syntax = "proto3";
package com.kotlingrpc.demoGrpc;

option java_multiple_files = true;
option java_package = "com.kotlingrpc.demoGrpc";
option java_outer_classname = "HelloWorldProto";

// The greeting service definition.
service Greeter {
  // Sends a greeting
  rpc SayHello (HelloRequest) returns (HelloReply) {}
}

// The request message containing the user's name.
message HelloRequest {
  string name = 1;
}

// The response message containing the greetings
message HelloReply {
  string message = 1;
}
```

Run gradlew build, then the generated files would be built on the `generatedFilesBaseDir` you define on `build.gradle.kts`

