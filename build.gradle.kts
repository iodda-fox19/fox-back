import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
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
}


tasks.withType<Test> {
    useJUnitPlatform()
}
