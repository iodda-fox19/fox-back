package com.mghostl.fox.controllers

import com.mghostl.fox.services.AuthService
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed(value = ["ADMIN", "USER"])
@RestController
@RequestMapping("api/sms")
class SmsController(
    private val authService: AuthService
) {

    companion object: KLogging()

    @PostMapping
    fun sendSms(@RequestParam phone: String): ResponseEntity<Unit> {
        logger.info { "Receive request for sending auth sms to phone $phone" }
        authService.sendAuthSms(phone)
        return ResponseEntity(HttpStatus.OK)
    }
}