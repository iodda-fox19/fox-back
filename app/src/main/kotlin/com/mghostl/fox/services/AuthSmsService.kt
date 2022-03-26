package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser

interface AuthSmsService {
    fun auth(phone: String, userId: Int): SmsDto
    fun checkCode(id: Int, code: String): SmsDtoWithUser
}