package com.mghostl.fox.dto

import java.time.LocalDate

data class UserRusGolfDTO(
    var golfRegistryIdRU: String? = null,
    var handicap: Float? = null,
    var handicapUpdateAt: LocalDate? = null,
    var fio: String? = null
)