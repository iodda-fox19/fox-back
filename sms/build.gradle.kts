plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.1")
    implementation("io.github.microutils:kotlin-logging:1.6.10")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.13.2")
}