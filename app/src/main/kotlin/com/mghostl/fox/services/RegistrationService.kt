package com.mghostl.fox.services

import com.mghostl.fox.dto.NewUserData
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.dto.UserDto

interface RegistrationService {
    fun sendSms(phone: String): SmsDto
    fun checkCode(smsId: Int, code: String): Pair<SmsDtoWithUser, String>
    fun createUser(phone: String, userData: NewUserData): Pair<UserDto, String>
}