import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    val kotlinVersion = "1.6.10"

    id("org.springframework.boot") version "2.6.4" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    kotlin("jvm") version kotlinVersion apply  false
    kotlin("plugin.spring") version kotlinVersion apply false
    kotlin("plugin.jpa") version kotlinVersion apply false
    kotlin("kapt") version kotlinVersion apply false
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/milestone")
        name = "spring-milestone"
    }
    maven{
        url = uri("https://repo.spring.io/snapshot")
        name = "spring-snapshots"
    }
    maven {
        name = "Sonatype Nexus Snapshots"
        url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
    }
}

allprojects {
    group = "com.mghostl"
    version = "0.0.1-SNAPSHOT"
    tasks.withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }
}

subprojects {

    apply {
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/milestone")
            name = "spring-milestone"
        }
        maven{
            url = uri("https://repo.spring.io/snapshot")
            name = "spring-snapshots"
        }
        maven {
            name = "Sonatype Nexus Snapshots"
            url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
        }
    }

    tasks.withType<Jar> {
        enabled = true
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
