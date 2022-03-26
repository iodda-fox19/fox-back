package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser

interface AuthService {
    fun sendAuthSms(phone: String): SmsDto
    fun checkCode(smsId: Int, code: String): Pair<SmsDtoWithUser, String>
}