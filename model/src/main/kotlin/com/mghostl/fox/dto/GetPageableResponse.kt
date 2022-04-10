package com.mghostl.fox.dto

abstract class GetPageableResponse<T>(
    val data: Set<T>,
    var count: Long
)