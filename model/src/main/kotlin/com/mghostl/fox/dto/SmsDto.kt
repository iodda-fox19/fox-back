package com.mghostl.fox.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class SmsDto(
    var id: Int? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var createdAt: LocalDateTime? = null,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
    var phone: String? = null
)
