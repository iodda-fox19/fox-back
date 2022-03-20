plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
    kotlin("kapt")
    id("com.google.cloud.tools.jib")

}

apply {
    from("../docker/docker.gradle")
}

object Version {
    const val springAdmin = "2.6.2"
    const val swagger = "1.6.0"
}

dependencies {
    // Spring
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.1")
    implementation("de.codecentric:spring-boot-admin-starter-server:${Version.springAdmin}")
    implementation("de.codecentric:spring-boot-admin-starter-client:${Version.springAdmin}")
    implementation("io.jsonwebtoken:jjwt:0.9.1")
    // modules
    implementation(project(":rusgolf"))
    implementation(project(":model"))
    implementation(project(":sms"))

    //Swagger
    implementation("org.springdoc:springdoc-openapi-data-rest:${Version.swagger}")
    implementation("org.springdoc:springdoc-openapi-ui:${Version.swagger}")
    implementation("org.springdoc:springdoc-openapi-kotlin:${Version.swagger}")

    // Kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // DB
    implementation("org.liquibase:liquibase-core")
    implementation("org.postgresql:postgresql:42.3.2")
    // Logging
    implementation("io.github.microutils:kotlin-logging:1.6.10")

    // Dev tools
    implementation("org.springframework.boot:spring-boot-devtools")

    // Tests
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("com.ninja-squad:springmockk:3.1.1")
    testImplementation("org.testcontainers:postgresql:1.16.3")

}

tasks.withType<Test> {
    useJUnitPlatform()
}