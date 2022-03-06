package com.mghostl.fox

import com.mghost.fox.CoreApplication
import com.mghostl.fox.config.DBTestContainersConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("unit")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CoreApplication::class])
@Import(DBTestContainersConfiguration::class)
abstract class AbstractTest {
}