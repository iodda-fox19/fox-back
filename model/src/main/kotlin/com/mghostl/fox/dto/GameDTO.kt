package com.mghostl.fox.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

data class GameDTO(
    var id: Int? = null,

    var users: Set<UserDto> = mutableSetOf(),

    var clubId: Int? = null,

    var countryId: Int? = null,

    @JsonFormat(pattern = "yyyy-MM-dd")
    var date: LocalDate? = null,

    var holes: Int? = null,

    var gamersCount: Int? = null,

    var reserved: Boolean? = null,

    var memberPrice: BigDecimal? = null,

    var guestPrice: BigDecimal? = null,

    @JsonFormat(pattern = "HH:mm")
    var time: LocalTime? = null,

    var description: String? = null,

    var name: String? = null,

    var onlyMembers: Boolean? = null,

    var handicapMin: Float? = null,

    var handicapMax: Float? = null
)
