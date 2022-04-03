package com.mghostl.fox.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Avatar(
    @JsonProperty("filename")
    val fileName: String,
    val url: String
)