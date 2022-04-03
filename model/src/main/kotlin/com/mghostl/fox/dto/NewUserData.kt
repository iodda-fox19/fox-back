package com.mghostl.fox.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Registering user model")
data class NewUserData(
    @field:Schema(
        description = "user's name",
        example = "Иван",
        type = "String"
    )
    val name: String
    )