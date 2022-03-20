package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto

interface AuthService {
    fun sendAuthSms(phone: String): SmsDto
    fun checkCode(smsId: Int, code: String): String
}