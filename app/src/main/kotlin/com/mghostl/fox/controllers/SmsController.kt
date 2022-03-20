package com.mghostl.fox.controllers

import com.mghostl.fox.auth.AuthResponse
import com.mghostl.fox.auth.AuthSmsRequest
import com.mghostl.fox.auth.CheckCodeRequest
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.services.AuthService
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/sms")
class SmsController(
    private val authService: AuthService
) {

    companion object: KLogging()

    @PostMapping
    fun sendSms(@RequestBody authSmsRequest: AuthSmsRequest): ResponseEntity<SmsDto> {
        val phone = authSmsRequest.phone
        logger.info { "Receive request for sending auth sms to phone $phone" }
        return authService.sendAuthSms(phone)
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("{smsId}")
    fun checkCode(@PathVariable smsId: Int, @RequestBody checkCodeRequest: CheckCodeRequest): ResponseEntity<Unit> {
        val code = checkCodeRequest.code
        logger.info { "Receive request for checking code $code for sms id $smsId" }
        return authService.checkCode(smsId, code)
            .let{ ResponseEntity.ok()
                .header("X-Authentication", it)
                .build()}
    }
}