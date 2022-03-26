package com.mghostl.fox.rusgolf.model

import com.mghostl.fox.model.Sex
import java.time.LocalDate

data class RusGolfUserDTO(
    val golfRegistryIdRU: String,
    val fio: String,
    val handicapUpdateAt: LocalDate,
    var handicap: Float,
    var sex: Sex
)
