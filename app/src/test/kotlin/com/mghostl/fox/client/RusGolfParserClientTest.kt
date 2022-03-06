package com.mghostl.fox.client

import com.mghost.fox.CoreApplication
import com.mghostl.fox.AbstractTest
import com.mghostl.fox.config.DBTestContainersConfiguration
import com.mghostl.fox.rusgolf.clients.RusGolfClient
import com.mghostl.fox.rusgolf.clients.RusGolfParserClient
import com.mghostl.fox.rusgolf.parsers.RusGolfParser
import com.mghostl.fox.utils.users
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("unit")
@SpringBootTest(classes = [CoreApplication::class], webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = [JpaRepositoriesAutoConfiguration::class])
@Import(DBTestContainersConfiguration::class)
class RusGolfParserClientTest{
    @MockkBean
    lateinit var rusGolfParser: RusGolfParser

    @Autowired
    lateinit var rusGolfParserClient: RusGolfClient

    @Test
    fun `should parse`() {
        val users = users()
        every { rusGolfParser.parse(1) } returns users
        every { rusGolfParser.parse(2) } returns emptySet()

        val response = rusGolfParserClient.getUserData()

        verify(exactly = 1) { rusGolfParser.parse(1) }
        verify(exactly = 1) { rusGolfParser.parse(2) }
        verify(exactly = 0) { rusGolfParser.parse(3) }
        assertEquals(response, users)
    }
}