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
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // MapStruct
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
    implementation("org.mapstruct:mapstruct-jdk8:$mapStructVersion")
}
tasks.withType<org.springframework.boot.gradle.tasks.bundling.BootJar> {
    enabled = false
}