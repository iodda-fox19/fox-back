package com.mghostl.fox.dto

data class GetComplaintsResponse(
    val complaints: Set<ComplaintDTO>,
    val count: Long
)