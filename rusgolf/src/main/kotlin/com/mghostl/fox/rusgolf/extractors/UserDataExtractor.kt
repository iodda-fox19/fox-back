package com.mghostl.fox.rusgolf.extractors

import com.mghostl.fox.model.Sex
import com.mghostl.fox.rusgolf.model.FieldType
import com.mghostl.fox.rusgolf.model.UserDTO
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class UserDataExtractor(
    private val fieldExtractors: List<FieldExtractor<*>>,
    private val sexFieldExtractor: SexFieldExtractor
) : DataExtractor<UserDTO> {

    override fun extract(data: List<String>, headers: List<String>) = UserDTO(
        golfRegistryIdRU = getExtractor(FieldType.ID).getString(data, headers),
        fio = getExtractor(FieldType.FIO).getString(data, headers),
        handicapUpdateAt = getExtractor(FieldType.UPDATE_DATE_HANDICAP).getLocalDate(data, headers),
        handicap = getExtractor(FieldType.HANDICAP).getFloat(data, headers),
        sex = getExtractor(FieldType.SEX).getSex(data, headers)
    )

    private fun getExtractor(fieldType: FieldType) = when (fieldType) {
        FieldType.SEX -> sexFieldExtractor
        else -> fieldExtractors.first { it.getFieldType() == fieldType }
    }

    private fun FieldExtractor<*>.getSex(data: List<String>, headers: List<String>) = extract(data, headers) as Sex
    private fun FieldExtractor<*>.getString(data: List<String>, headers: List<String>) =
        extract(data, headers) as String

    private fun FieldExtractor<*>.getLocalDate(data: List<String>, headers: List<String>) =
        extract(data, headers) as LocalDate

    private fun FieldExtractor<*>.getFloat(data: List<String>, headers: List<String>) = extract(data, headers) as Float
}