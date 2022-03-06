package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.rusgolf.model.FieldType
import com.mghostl.fox.rusgolf.model.UserDTO
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserDataExtractor(
    private val fieldExtractors: List<FieldExtractor<out Any>>
): DataExtractor<UserDTO> {

    override fun extract(data: List<String>, headers: List<String>) = UserDTO(
        golfRegistryIdRU = getExtractor(FieldType.ID).getString(data, headers),
        fio = getExtractor(FieldType.FIO).getString(data, headers),
        handicapUpdateAt = getExtractor(FieldType.UPDATE_DATE_HANDICAP).getLocalDate(data, headers),
        handicap = getExtractor(FieldType.HANDICAP).getFloat(data, headers)
    )

    private fun getExtractor(fieldType: FieldType) = fieldExtractors.first { it.getFieldType() == fieldType }

    private fun FieldExtractor<out Any>.getString(data: List<String>, headers: List<String>) = extract(data, headers) as String

    private fun FieldExtractor<out Any>.getLocalDate(data: List<String>, headers: List<String>) = extract(data, headers) as LocalDate

    private fun FieldExtractor<out Any>.getFloat(data: List<String>, headers: List<String>) = extract(data, headers) as Float
}