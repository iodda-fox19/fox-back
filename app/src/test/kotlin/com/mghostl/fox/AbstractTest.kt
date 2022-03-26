package com.mghostl.fox

import com.mghostl.fox.CoreApplication
import com.mghostl.fox.config.DBTestContainersConfiguration
import de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("unit")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CoreApplication::class])
@Import(DBTestContainersConfiguration::class)
@EnableAutoConfiguration(exclude = [AdminServerDiscoveryAutoConfiguration::class])
abstract class AbstractTest