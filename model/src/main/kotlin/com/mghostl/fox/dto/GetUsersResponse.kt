package com.mghostl.fox.dto

data class GetUsersResponse(
    val users: Set<UserDto>,
    val count: Long
)
