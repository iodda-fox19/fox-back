package com.mghostl.fox.dto

import com.mghostl.fox.model.Avatar

data class UserDto(
    var id: Int? = null,

    var name: String? = null,

    var lastName: String? = null,

    var isGamer: Boolean? = null,

    var isTrainer: Boolean? = null,

    var isAdmin: Boolean? = null,

    var isSubmittedTrainer: Boolean = false,

    var isSubmittedAdministrator: Boolean = false,

    var handicap: Float? = null,

    var isSubmittedHandicap: Boolean = false,

    var golfRegistryIdRU: String? = null,

    var homeClub: String? = null,

    var toAddEventsInCalendar: Boolean? = null,

    var isBlocked: Boolean? = null,

    var isDeleted: Boolean? = null,

    var avatar: Avatar? = null,

    var phone: String? = null
)