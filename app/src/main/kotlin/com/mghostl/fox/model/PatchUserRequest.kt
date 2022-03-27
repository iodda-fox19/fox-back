package com.mghostl.fox.model

data class PatchUserRequest(
    val isSubmittedTrainer: Boolean? = null,
    val isSubmittedAdmin: Boolean? = null,
    val isBlocked: Boolean? = null,
    val isDeleted: Boolean? = null
)
