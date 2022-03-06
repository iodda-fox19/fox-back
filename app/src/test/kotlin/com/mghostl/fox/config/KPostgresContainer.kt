package com.mghostl.fox.config

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class KPostgresContainer: PostgreSQLContainer<KPostgresContainer>(DockerImageName.parse("postgres:11.1")
    .asCompatibleSubstituteFor("postgres")) {

    override fun close() {}
}