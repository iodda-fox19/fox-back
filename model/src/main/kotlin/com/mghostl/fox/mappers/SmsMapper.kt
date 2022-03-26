package com.mghostl.fox.mappers

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.model.Sms
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface SmsMapper {
    fun map(sms: Sms): SmsDto
}

@Mapper
interface SmsWithUserMapper {
    @Mappings(
        Mapping(target = "user.id", source = "userId")
    )
    fun map(sms: Sms): SmsDtoWithUser
}