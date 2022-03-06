package com.mghostl.fox.services

import com.mghost.fox.CoreApplication
import com.mghostl.fox.AbstractTest
import com.mghostl.fox.config.DBTestContainersConfiguration
import com.mghostl.fox.rusgolf.clients.RusGolfClient
import com.mghostl.fox.rusgolf.services.RusGolfService
import com.mghostl.fox.utils.users
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("unit")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [CoreApplication::class])
@Import(DBTestContainersConfiguration::class)
class RusGolfServiceTest {
    @MockkBean
    lateinit var rusGolfClient: RusGolfClient

    @Autowired
    lateinit var rusGolfService: RusGolfService

    @Test
    fun `should get user data`() {
        val users = users()
        every { rusGolfClient.getUserData() } returns users

        val response = rusGolfService.getUsersData()

        assertEquals(users, response)
     }
}