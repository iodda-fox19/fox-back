package com.mghostl.fox.model

data class GameFilter(
    val countryId: Int? = null,
    val clubId: Int? = null,
    val numOfHoles: Int? = null,
    val gamersCount: Int? = null,
    val handicapMin: Float? = null, // TODO complete ( add columns)
    val handicapMax: Float? = null,
    val onlyForMembers: Boolean? = null
)