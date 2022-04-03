package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.mappers.SmsWithUserMapper
import com.mghostl.fox.model.Sms
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.sms.model.SmsExpiredException
import com.mghostl.fox.sms.model.SmsUserNotFoundException
import com.mghostl.fox.sms.model.WrongSmsCodeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class AuthSmsServiceImpl(
    private val smsCodeService: SmsCodeService
): AuthSmsService {

    override fun auth(phone: String, userId: Int) = smsCodeService.sendNewCode(phone, "Authentication code:", userId)

    override fun checkCode(id: Int, code: String) = smsCodeService.checkCode(id, code)

}