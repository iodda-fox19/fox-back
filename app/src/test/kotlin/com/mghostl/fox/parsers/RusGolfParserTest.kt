package com.mghostl.fox.parsers

import com.mghostl.fox.rusgolf.extractors.UserDataExtractor
import com.mghostl.fox.rusgolf.parsers.RusGolfParserImpl
import com.mghostl.fox.rusgolf.properties.RusGolfProperties
import com.mghostl.fox.utils.rusGolfUserDTO
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * Not stable test. It uses internet connection with rusGolf site
 */
class RusGolfParserTest {
    private val rusGolfProperties = RusGolfProperties("https://hcp.rusgolf.ru/public/player/ru/?page=")
    private val dataExtractor: UserDataExtractor = mockk()
    private val rusGolfParser = RusGolfParserImpl(rusGolfProperties, dataExtractor)

    @Test
    fun `should parse rusgolf`() {
        every { dataExtractor.extract(any(), any()) } returns rusGolfUserDTO()

        val response = rusGolfParser.parse(1)

        assertNotNull(response)
        verify(exactly = 50) { dataExtractor.extract(any(), any())}
        assertEquals(response.size, 1)
    }
}