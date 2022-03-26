package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.mappers.SmsMapper
import com.mghostl.fox.mappers.SmsWithUserMapper
import com.mghostl.fox.model.Sms
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.sms.model.SmsExpiredException
import com.mghostl.fox.sms.model.SmsUserNotFoundException
import com.mghostl.fox.sms.model.SmsWasSentRecentlyException
import com.mghostl.fox.sms.model.WrongSmsCodeException
import com.mghostl.fox.sms.services.SmsService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class AuthSmsServiceImpl(
    private val smsRepository: SmsRepository,
    private val smsService: SmsService,
    private val smsMapper: SmsMapper,
    private val smsDtoWithUserMapper: SmsWithUserMapper
): AuthSmsService {

    @Value("\${sms-ru.timeout}")
    private lateinit var timeout: String

    @Transactional
    override fun auth(phone: String, userId: Int): SmsDto {
        // checking if sms was sent recently
        if(smsWasSendRecently(phone)) {
            throw SmsWasSentRecentlyException("Sms has already been sent for number $phone. Try again later")
        }
        val code = generateCode()
        smsService.send("Authentication code: $code", phone)
        return smsRepository.save(Sms(phone = phone, sendedCode = code, userId = userId))
            .let { smsMapper.map(it) }
            .apply {
                val now = LocalDateTime.now()
                if(createdAt == null) createdAt = now
                if(updatedAt == null) updatedAt = now
            }
    }

    override fun checkCode(id: Int, code: String): SmsDtoWithUser {
        val smsOpt = smsRepository.findById(id)
        if(smsOpt.isEmpty) {
            throw SmsUserNotFoundException("There is no sms for this id $id")
        }
        val sms = smsOpt.get()
        if(sms.wasExpired()) {
            throw SmsExpiredException("Sms code $code was expired")
        }
        if(sms.sendedCode != code) {
            throw WrongSmsCodeException("Wrong sms code")
        }
        return smsDtoWithUserMapper.map(sms)
    }

    private fun Sms.wasExpired() = code != null || createdAt!!.plusSeconds(timeout.toLong()).isBefore(LocalDateTime.now())
    private fun smsWasSendRecently(phone: String) = findByPhone(phone)?.createdAt?.plusSeconds(timeout.toLong())
        ?.isAfter(LocalDateTime.now()) ?: false

    private fun findByPhone(phone: String) = smsRepository.findAllByPhone(phone)
        .maxByOrNull { it.createdAt!! }

    private fun generateCode() = (1000..9999).random().toString()
}