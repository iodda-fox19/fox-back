package com.mghostl.fox.extractors

import com.mghostl.fox.rusgolf.extractors.UpdateDateHandicapExtractor
import com.mghostl.fox.rusgolf.model.FieldType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate

class HandicapUpdateDateFieldExtractorTest {
    private val handicapUpdateDateExtractor = UpdateDateHandicapExtractor()

    @Test
    fun `should extract update date`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "52,2", "04.09.2021")
        val headers = listOf("№", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")

        val response = handicapUpdateDateExtractor.extract(data, headers)

        assertEquals(LocalDate.of(2021, 9, 4), response)
    }


    @Test
    fun `should throw exception if there is no appropriate header`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "52,2", "04.09.2021")
        val headers = listOf("N", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCPf")

        assertThrows<IllegalArgumentException> { handicapUpdateDateExtractor.extract(data, headers) }
            .apply { assertTrue(message!!.contains("Couldn't extract value")) }


    }

    @Test
    fun `field type is update date`() {
        assertEquals(handicapUpdateDateExtractor.getFieldType(), FieldType.UPDATE_DATE_HANDICAP)
    }
}