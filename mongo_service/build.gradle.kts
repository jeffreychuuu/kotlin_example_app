import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.4.11"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.32"
    id("com.netflix.dgs.codegen") version "5.1.2"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:1.5.2")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb:2.5.5")
    implementation("org.springframework.boot:spring-boot-starter-data-redis:2.5.5")
    implementation("redis.clients:jedis:3.7.0")
    implementation("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:4.9.0")
    implementation("org.springframework.kafka:spring-kafka:2.7.7")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

@OptIn(kotlin.ExperimentalStdlibApi::class)
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
    generateClient = true
    packageName = "com.example.mongo_service.generated"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
