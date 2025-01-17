package com.mghostl.fox

import com.fasterxml.jackson.databind.ObjectMapper
import com.mghostl.fox.config.DBTestContainersConfiguration
import de.codecentric.boot.admin.server.cloud.config.AdminServerDiscoveryAutoConfiguration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@Sql(scripts = ["classpath:clean.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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

    fun MockHttpServletRequestBuilder.json(value: Any) = contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(value))

    fun <T> ResultActions.andGetResponse(clazz: Class<T>): T = andReturn()
        .response.contentAsString.let { objectMapper.readValue(it, clazz) }

    fun ResultActions.andExpectJson(value: Any) = andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(objectMapper.writeValueAsString(value)))

    fun MockHttpServletRequestBuilder.param(name: String, value: Any) = param(name, value.toString())
}