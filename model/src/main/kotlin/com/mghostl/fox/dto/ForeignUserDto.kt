package com.mghostl.fox.dto

data class ForeignUserDto(
    var id: Int? = null,

    var name: String? = null,

    var lastName: String? = null,

    var isGamer: Boolean? = false,

    var isTrainer: Boolean? = false,

    var isAdmin: Boolean? = false,

    var isSubmittedTrainer: Boolean = false,

    var isSubmittedAdministrator: Boolean = false,

    var handicap: Float? = null,

    var isSubmittedHandicap: Boolean = false,

    var golfRegistryIdRU: String? = null,

    var homeClub: String? = null,

    var isBlocked: Boolean? = null,

    var avatar: String? = null
)