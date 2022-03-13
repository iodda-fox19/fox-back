package com.mghostl.fox.services

import com.mghostl.fox.model.Sms
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.sms.model.SmsWasSentRecentlyException
import com.mghostl.fox.sms.services.SmsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class AuthSmsServiceImpl(
    private val smsRepository: SmsRepository,
    private val smsService: SmsService
): AuthSmsService {

    @Value("\${sms-ru.timeout}")
    private lateinit var timeout: String

    @Transactional
    override fun auth(phone: String, userId: Int) {
        // checking if sms was sent recently
        if(smsWasSendRecently(phone)) {
            throw SmsWasSentRecentlyException("Sms has already been sent for number $phone. Try again later")
        }
        val code = generateCode()
        smsService.send("Authentication code: $code", phone)
        smsRepository.save(Sms(phone = phone, sendedCode = code, userId = userId))
    }

    private fun smsWasSendRecently(phone: String) = findByPhone(phone)?.createdAt?.plusMinutes(timeout.toLong())
        ?.isAfter(LocalDateTime.now()) ?: false

    private fun findByPhone(phone: String) = smsRepository.findAllByPhone(phone)
        .maxByOrNull { it.createdAt!! }

    private fun generateCode() = (1000..9999).random().toString()
}