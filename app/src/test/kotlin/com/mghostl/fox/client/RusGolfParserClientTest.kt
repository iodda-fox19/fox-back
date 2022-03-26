package com.mghostl.fox.client

import com.mghostl.fox.AbstractTest
import com.mghostl.fox.rusgolf.clients.RusGolfClient
import com.mghostl.fox.rusgolf.parsers.RusGolfParser
import com.mghostl.fox.utils.rusGolfUsers
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class RusGolfParserClientTest: AbstractTest(){
    @MockkBean
    lateinit var rusGolfParser: RusGolfParser

    @Autowired
    lateinit var rusGolfParserClient: RusGolfClient

    @Test
    fun `should parse`() {
        val users = rusGolfUsers()
        every { rusGolfParser.parse(1) } returns users
        every { rusGolfParser.parse(2) } returns emptySet()

        val response = rusGolfParserClient.getUserData()

        verify(exactly = 1) { rusGolfParser.parse(1) }
        verify(exactly = 1) { rusGolfParser.parse(2) }
        verify(exactly = 0) { rusGolfParser.parse(3) }
        assertEquals(response, users)
    }
}