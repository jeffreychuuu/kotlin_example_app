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

- [Get Started](#Get -Started)
- [Adding Rest Controller](#Adding-Rest-Controller)
- [Adding SpringDoc OpenAPI3 Support](#Adding -SpringDoc-OpenAPI3-Support)
- [Adding Graphql Support](#Adding-Graphql-Support)

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

