package com.mghostl.fox.services

import com.mghostl.fox.sms.model.SmsUserNotFoundException
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authSmsService: AuthSmsService,
    private val userService: UserService
): AuthService {

    companion object: KLogging()

    override fun sendAuthSms(phone: String) {
        logger.info { "Auth user by phone $phone" }
        val user = userService.findByPhone(phone) ?: throw SmsUserNotFoundException("There is no user with such phone $phone")
        authSmsService.auth(phone, user.id!!)
    }
}