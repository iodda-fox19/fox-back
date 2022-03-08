package com.mghostl.fox.extractors

import com.mghostl.fox.model.Sex
import com.mghostl.fox.rusgolf.extractors.FIOFieldExtractor
import com.mghostl.fox.rusgolf.extractors.HandicapFieldExtractor
import com.mghostl.fox.rusgolf.extractors.IdFieldExtractor
import com.mghostl.fox.rusgolf.extractors.SexFieldExtractor
import com.mghostl.fox.rusgolf.extractors.UpdateDateHandicapExtractor
import com.mghostl.fox.rusgolf.extractors.UserDataExtractor
import com.mghostl.fox.rusgolf.model.UserDTO
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class UserDataExtractorTest {
    private val fieldExtractors = listOf(FIOFieldExtractor(),
        HandicapFieldExtractor(),
        IdFieldExtractor(),
        UpdateDateHandicapExtractor())
    private val dataExtractor = UserDataExtractor(fieldExtractors, SexFieldExtractor())

    @Test
    fun `should extract userDTO`() {
        val data = listOf("RU004947", "Авдеева Наталия Витальевна", "Муж.", "52,2", "04.09.2021")
        val headers = listOf("№", "Фамилия, Имя, Отчество", "Пол", "HI", "Дата обновления HCP")
        val expectUser = UserDTO("RU004947", "Авдеева Наталия Витальевна", LocalDate.of(2021, 9, 4), 52.2f, Sex.MALE)

        val response = dataExtractor.extract(data, headers)

        assertEquals(expectUser, response)
    }
}