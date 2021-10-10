import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*;

buildscript {
	dependencies {
		classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
	}
}

plugins {
	id("com.google.protobuf") version "0.8.13"
//	kotlin("jvm") version "1.4.32" // remove the version tag if you are build by other project
	kotlin("jvm")
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
	compileOnly("javax.annotation:javax.annotation-api:1.3.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	// gRPC
	api("io.grpc:grpc-kotlin-stub:0.2.1")
	implementation("io.grpc:protoc-gen-grpc-kotlin:1.1.0")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"

	}
}

protobuf {
	protoc{
		artifact = "com.google.protobuf:protoc:3.14.0:osx-x86_64" // you need to mention :osx-x86_64 behind if you are using m1 mac
	}
	generatedFilesBaseDir = "$projectDir/src/main/kotlin/generated"
	plugins {
		id("grpc"){
			artifact = "io.grpc:protoc-gen-grpc-java:1.36.0:osx-x86_64" // you need to mention :osx-x86_64 behind if you are using m1 mac
		}
		id("grpckt") {
			artifact = "io.grpc:protoc-gen-grpc-kotlin:1.1.0:jdk7@jar"
		}
	}
	generateProtoTasks {
		ofSourceSet("main").forEach { generateProtoTask ->
			generateProtoTask
				.plugins {
					id("grpc")
					id("grpckt")
				}
		}
	}
}