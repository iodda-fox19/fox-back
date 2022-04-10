package com.mghostl.fox.dto

import javax.validation.constraints.NotNull

data class ComplaintDTO(

    var id: Int? = null,

    @field:NotNull
    var indictedUserId: Int? = null,

    @field:NotNull
    var comment: String? = null,
    )