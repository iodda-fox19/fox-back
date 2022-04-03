package com.mghostl.fox.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Model for sended sms")
data class SmsDto(
    @field:Schema(
        description = "id in db",
        example = "123",
        type = "Int"
    )
    var id: Int? = null,
    @field:Schema(
        description = "sms creation time",
        example = "2021-10-31T00:01:23",
        type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = null,
    @field:Schema(
        description = "sms updation time",
        example = "2021-10-31T00:01:23",
        type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
    @field:Schema(
        description = "phone on which sms was sent",
        example = "89605451593",
        type = "String"
    )
    var phone: String? = null
)

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Model for verified sms code")
data class SmsDtoWithUser(
    @field:Schema(
        description = "id in db",
        example = "123",
        type = "Int"
    )
    var id: Int? = null,
    @field:Schema(
        description = "sms creation time",
        example = "2021-10-31T00:01:23",
        type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = null,
    @field:Schema(
        description = "sms updation time",
        example = "2021-10-31T00:01:23",
        type = "LocalDateTime"
    )
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
    @field:Schema(
        description = "user's data",
        type = "UserDto"
    )
    var user: UserData ? = null,
    @field:Schema(
        description = "user's phone",
        example = "89992341232",
        type = "String"
    )
    var phone: String? = null
)

data class UserData(
    @field:Schema(
        description = "id in db",
        example = "123",
        type = "Int"
    )
    var id: Int? = null
)
