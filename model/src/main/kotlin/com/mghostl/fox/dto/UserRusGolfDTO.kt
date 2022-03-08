package com.mghostl.fox.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(description = "Model for user which was received from rusgolf")
data class UserRusGolfDTO(
    @field:Schema(
        description = "id in rusgolf",
        example = "RU000871",
        type = "String"
    )
    var golfRegistryIdRU: String? = null,
    @field:Schema(
        description = "User's handicap",
        example = "15.2",
        type = "float"
    )
    var handicap: Float? = null,
    @field:Schema(
        description = "Updating date of handicap",
        example = "2021-10-31",
        type = "LocalDate"
    )
    var handicapUpdateAt: LocalDate? = null,
    @field:Schema(
        description = "User's fio",
        example = "Абахов Олег Евгеньевич",
        type = "String"
    )
    var fio: String? = null
)