package com.mghostl.fox.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.mghostl.fox.AbstractTest
import com.mghostl.fox.model.UserRusGolf
import com.mghostl.fox.repository.UserRusGolfRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@WithMockUser(username = "admin", password = "admin", roles = ["ADMIN"])
@AutoConfigureMockMvc
class RusGolfUserControllerTest: AbstractTest() {

    @Autowired
    lateinit var mvc: MockMvc

    @Autowired
    lateinit var userRusGolfRepository: UserRusGolfRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    companion object {
        const val golfRegistryIdRU = "RU11"
        const val basePath = "/api/rusgolf"

    }
    val rusGolfUser = UserRusGolf(golfRegistryIdRU, 77.5f, LocalDate.now())

    @AfterEach
    fun afterEach(){
        userRusGolfRepository.deleteAll()
    }

    @Test
    fun `should return rusgolf user by its id`() {
        userRusGolfRepository.save(rusGolfUser)

        mvc.perform(get(basePath)
            .param("golfRegistryIdRU", golfRegistryIdRU))
            .andExpect(status().isOk)
            .andExpect(content().json(objectMapper.writeValueAsString(rusGolfUser)))
    }

    @Test
    fun `should return 404 if there is no user with such id`() {
        mvc.perform(get("/api/rusgolf")
            .param("golfRegistryIdRU", golfRegistryIdRU))
            .andExpect(status().isNotFound)
    }
}