package com.mghostl.fox.extractors

import com.mghostl.fox.rusgolf.extractors.IdFieldExtractor
import com.mghostl.fox.rusgolf.model.FieldType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class IdFieldExtractorTest {

    private val fieldIdExtractor = IdFieldExtractor()

    @Test
    fun `should extract id`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "04.09.2021", "52,2")
        val headers = listOf("№", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")

        val response = fieldIdExtractor.extract(data, headers)

        assertEquals("RU004947", response)
    }


    @Test
    fun `should throw exception if there is no appropriate header`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "04.09.2021", "52,2")
        val headers = listOf("N", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")

        assertThrows<IllegalArgumentException> { fieldIdExtractor.extract(data, headers) }
            .apply { assertTrue(message!!.contains("Couldn't extract value")) }


    }

    @Test
    fun `field type is id`() {
        assertEquals(fieldIdExtractor.getFieldType(), FieldType.ID)
    }
}
