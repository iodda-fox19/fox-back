package com.mghostl.fox.extractors

import com.mghostl.fox.rusgolf.extractors.HandicapFieldExtractor
import com.mghostl.fox.rusgolf.model.FieldType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class HandicapFieldExtractorTest {
    private val handicapFieldExtractor = HandicapFieldExtractor()

    @Test
    fun `should extract handicap`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "52,2", "04.09.2021")
        val headers = listOf("№", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")

        val response = handicapFieldExtractor.extract(data, headers)

        assertEquals(52.2f, response)
    }


    @Test
    fun `should throw exception if there is no appropriate header`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "52,2", "04.09.2021")
        val headers = listOf("N", "Фамилия, Имя, Отчество", "Пол", "HIC", "Дата обновления HCP")

        assertThrows<IllegalArgumentException> { handicapFieldExtractor.extract(data, headers) }
            .apply { assertTrue(message!!.contains("Couldn't extract value")) }


    }

    @Test
    fun `field type is id`() {
        assertEquals(handicapFieldExtractor.getFieldType(), FieldType.HANDICAP)
    }
}