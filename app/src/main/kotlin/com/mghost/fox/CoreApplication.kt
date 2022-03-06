package com.mghost.fox

import com.mghostl.fox.rusgolf.properties.RusGolfProperties
import de.codecentric.boot.admin.server.config.EnableAdminServer
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableAdminServer
@SpringBootApplication(scanBasePackages = ["com.mghostl.fox"])
@EnableConfigurationProperties(RusGolfProperties::class)
class CoreApplication

fun main(args: Array<String>) {
	runApplication<CoreApplication>(*args)
}
