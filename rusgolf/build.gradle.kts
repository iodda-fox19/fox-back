plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
}

val mapStructVersion = "1.3.1.Final"

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
    }
}

dependencies {
    // HTML Parsing
    implementation("org.jsoup:jsoup:1.14.3")
    implementation(project(":model"))


    // Spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")


    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
    implementation("org.mapstruct:mapstruct-jdk8:$mapStructVersion")

    // Logging
    implementation("io.github.microutils:kotlin-logging:1.6.10")

    // Kotlin
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")


    kapt("org.springframework.boot:spring-boot-configuration-processor")
}

tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}