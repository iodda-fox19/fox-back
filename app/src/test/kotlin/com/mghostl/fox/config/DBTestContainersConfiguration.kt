package com.mghostl.fox.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy
import javax.sql.DataSource

@TestConfiguration
class DBTestContainersConfiguration {

    companion object {
        private fun springLiquibase(dataSource: DataSource, liquibaseProperties: LiquibaseProperties) = SpringLiquibase()
                .apply {
                    this.dataSource = dataSource
                    changeLog = liquibaseProperties.changeLog
                    contexts = liquibaseProperties.contexts
                    defaultSchema = liquibaseProperties.defaultSchema
                    isDropFirst = liquibaseProperties.isDropFirst
                    setShouldRun(liquibaseProperties.isEnabled)
                    labels = liquibaseProperties.labels
                    setChangeLogParameters(liquibaseProperties.parameters)
                    setRollbackFile(liquibaseProperties.rollbackFile)
                }
    }

    private val db = KPostgresContainer()

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.liquibase")
    fun liquibaseProperties() = LiquibaseProperties()

    @Bean
    @Primary
    fun liquibase(dataSource: DataSource, liquibaseProperties: LiquibaseProperties) = springLiquibase(dataSource, liquibaseProperties)

    @Bean
    @Primary
    fun dataSource() = LogMessageWaitStrategy()
        .withRegEx(".*database system is ready to accept connections.*\\s")
        .withTimes(2)
        .also { db.waitingFor(it) }
        .also { db.start() }
        .let { HikariConfig().apply {
            driverClassName = db.driverClassName
            jdbcUrl = db.jdbcUrl
            username = db.username
            password = db.password
            maximumPoolSize = 15
        } }
        .let { HikariDataSource(it) }
}