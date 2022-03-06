package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class UpdateDateHandicapExtractor: FieldExtractor<LocalDate>() {

    companion object {
        val DATE_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    }

    override fun getFieldType() = FieldType.UPDATE_DATE_HANDICAP

    override fun List<String>.getValue(index: Int): LocalDate = try { get(index)
        .let {
            if(it.isEmpty()) LocalDate.now()
            else LocalDate.parse(it, DATE_PATTERN)
        }
    } catch (e: Exception) {
        if(get(index).isEmpty()) LocalDate.now( )
        else throw e

    }
}