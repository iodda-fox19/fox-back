package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto

interface AuthSmsService {
    fun auth(phone: String, userId: Int): SmsDto
    fun checkCode(id: Int, code: String): Int
}