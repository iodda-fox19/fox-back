package com.mghostl.fox.services

import com.mghostl.fox.AbstractTest
import com.mghostl.fox.rusgolf.clients.RusGolfClient
import com.mghostl.fox.rusgolf.services.RusGolfService
import com.mghostl.fox.utils.rusGolfUsers
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RusGolfServiceTest: AbstractTest() {
    @MockkBean
    lateinit var rusGolfClient: RusGolfClient

    @Autowired
    lateinit var rusGolfService: RusGolfService

    @Test
    fun `should get user data`() {
        val users = rusGolfUsers()
        every { rusGolfClient.getUserData() } returns users

        val response = rusGolfService.getUsersData()

        assertEquals(users, response)
     }
}