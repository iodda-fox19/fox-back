package com.mghostl.fox.extractors

import com.mghostl.fox.rusgolf.extractors.FIOFieldExtractor
import com.mghostl.fox.rusgolf.model.FieldType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class FIOFieldExtractorTest {
    private val fioFieldExtractor = FIOFieldExtractor()

    @Test
    fun `should extract fio`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "04.09.2021", "52,2")
        val headers = listOf("№", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")

        val response = fioFieldExtractor.extract(data, headers)

        assertEquals("Авдеева Наталия Витальевна", response)
    }


    @Test
    fun `should throw exception if there is no appropriate header`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "04.09.2021", "52,2")
        val headers = listOf("N", "FIO", "Пол", "HI", "Дата обновления HCP")

        assertThrows<IllegalArgumentException> { fioFieldExtractor.extract(data, headers) }
            .apply { assertTrue(message!!.contains("Couldn't extract value")) }


    }

    @Test
    fun `field type is id`() {
        assertEquals(fioFieldExtractor.getFieldType(), FieldType.FIO)
    }
}