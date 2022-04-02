package com.mghostl.fox.converters

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.mghostl.fox.model.Avatar
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter
class AvatarConverter: AttributeConverter<Avatar, String> {
    private val objectMapper = jacksonObjectMapper()

    override fun convertToDatabaseColumn(attribute: Avatar?) = attribute?.let { objectMapper.writeValueAsString(it) }

    override fun convertToEntityAttribute(dbData: String?) = dbData?.let { objectMapper.readValue(it, Avatar::class.java) }
}