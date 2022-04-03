package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser

interface SmsCodeService {
    fun sendNewCode(phone: String, message: String, userId: Int? = null): SmsDto
    fun checkCode(smsId: Int, code: String): SmsDtoWithUser
}