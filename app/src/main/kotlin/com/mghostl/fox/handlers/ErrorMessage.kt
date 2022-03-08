package com.mghostl.fox.handlers

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Model for error responses")
data class ErrorMessage(
    @field:Schema(description = "Error's text message")
    val error: String
)
