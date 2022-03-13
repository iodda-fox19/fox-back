package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.mappers.UserRusGolfMapper
import com.mghostl.fox.model.Sex
import com.mghostl.fox.model.UserRusGolf
import com.mghostl.fox.repository.UserRusGolfRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

class RusGolfUserControllerTest: AbstractMvcTest("/api/rusgolf") {

    @Autowired
    lateinit var userRusGolfRepository: UserRusGolfRepository

    @Autowired
    lateinit var rusGolfMapper: UserRusGolfMapper

    companion object {
        const val golfRegistryIdRU = "RU11"

    }
    val rusGolfUser = UserRusGolf(golfRegistryIdRU, 77.5f, LocalDate.of(2022, 8, 3),
    LocalDate.of(2022, 8, 3).atTime(0, 0),
        Sex.MALE, "Лев", "Зильберман", "Михайлович")

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
            .andExpect(content().json(objectMapper.writeValueAsString(rusGolfMapper.map(rusGolfUser))))
    }

    @Test
    fun `should return 404 if there is no user with such id`() {
        mvc.perform(get("/api/rusgolf")
            .param("golfRegistryIdRU", golfRegistryIdRU))
            .andExpect(status().isNotFound)
    }
}