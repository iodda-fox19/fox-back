package com.mghost.fox

import com.mghost.fox.config.LocalSecurityConfiguration
import com.mghostl.fox.rusgolf.properties.RusGolfProperties
import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableAdminServer
@SpringBootApplication(scanBasePackages = ["com.mghostl"], scanBasePackageClasses = [LocalSecurityConfiguration::class, TestController::class])
@EnableConfigurationProperties(RusGolfProperties::class)
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.mghostl.fox"])
@EntityScan(basePackages = ["com.mghostl.fox"])
class CoreApplication

fun main(args: Array<String>) {
	runApplication<CoreApplication>(*args)
}
