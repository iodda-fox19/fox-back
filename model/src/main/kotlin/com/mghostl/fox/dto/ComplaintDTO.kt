package com.mghostl.fox.dto

import javax.validation.constraints.NotNull

data class ComplaintDTO(

    @field:NotNull
    var indictedUserId: Int? = null,

    @field:NotNull
    var comment: String? = null,
    )