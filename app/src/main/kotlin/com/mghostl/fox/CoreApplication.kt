package com.mghostl.fox

import com.mghostl.fox.rusgolf.properties.RusGolfProperties
import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

const val BASE_PACKAGE = "com.mghostl.fox"

@EnableAdminServer
@SpringBootApplication(scanBasePackages = [BASE_PACKAGE])
@EnableConfigurationProperties(RusGolfProperties::class)
@EnableScheduling
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = [BASE_PACKAGE])
@EntityScan(basePackages = [BASE_PACKAGE])
class CoreApplication {
	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			runApplication<CoreApplication>(*args)
		}
	}

}
