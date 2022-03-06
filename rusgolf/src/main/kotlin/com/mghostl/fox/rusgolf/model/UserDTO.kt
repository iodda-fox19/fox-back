package com.mghostl.fox.rusgolf.model

import java.time.LocalDate

data class UserDTO(
    val golfRegistryIdRU: String,
    val fio: String,
    val handicapUpdateAt: LocalDate,
    var handicap: Float
)
