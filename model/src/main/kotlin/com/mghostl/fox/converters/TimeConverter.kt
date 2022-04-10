package com.mghostl.fox.converters

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class TimeConverter: AttributeConverter<LocalTime, String> {
    override fun convertToDatabaseColumn(attribute: LocalTime?) = attribute
        ?.let { DateTimeFormatter.ofPattern("HH:mm").format(it) }

    override fun convertToEntityAttribute(dbData: String?) = dbData
        ?.let { LocalTime.parse(dbData, DateTimeFormatter.ofPattern("HH:mm")) }
}