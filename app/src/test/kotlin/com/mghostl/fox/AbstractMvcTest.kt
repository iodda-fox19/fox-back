package com.mghostl.fox

import com.fasterxml.jackson.databind.ObjectMapper
import com.mghostl.fox.config.DBTestContainersConfiguration
import de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc

@WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
@AutoConfigureMockMvc
@ActiveProfiles("unit")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CoreApplication::class])
@Import(DBTestContainersConfiguration::class)
@EnableAutoConfiguration(exclude = [AdminServerDiscoveryAutoConfiguration::class])
abstract class AbstractMvcTest(
    protected val basePath: String
){
    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper
}