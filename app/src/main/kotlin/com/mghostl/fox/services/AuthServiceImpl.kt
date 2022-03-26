package com.mghostl.fox.services

import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.sms.model.SmsUserNotFoundException
import com.mghostl.fox.utils.JwtTokenUtil
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
    private val authSmsService: AuthSmsService,
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil
): AuthService {

    companion object: KLogging()

    override fun sendAuthSms(phone: String): SmsDto {
        logger.info { "Auth user by phone $phone" }
        val user = userService.findByPhone(phone) ?: throw SmsUserNotFoundException("There is no user with such phone $phone")
        return authSmsService.auth(phone, user.id!!)
    }

    override fun checkCode(smsId: Int, code: String): Pair<SmsDtoWithUser, String> {

        val smsDtoWithUser = authSmsService.checkCode(smsId, code)

        val token = smsDtoWithUser.user!!.id.let { userService.findById(it!!) }
            .let { FoxUserDetails(it) }
            .let { jwtTokenUtil.generateToken(it) }
        return smsDtoWithUser to token
    }
}