package com.mghostl.fox.controllers

import com.mghostl.fox.auth.SmsRequest
import com.mghostl.fox.auth.CheckCodeRequest
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: AuthService
) {

    companion object: KLogging()

    @Operation(summary = "Sending sms to user",
        description = "sending to the phone via sms ru")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200"),
            ApiResponse(description = "There is no user with such phone", responseCode = "404"),
            ApiResponse(description = "Sms has already been send", responseCode = "403")
        ]
    )
    @PostMapping
    fun sendSms(@RequestBody authSmsRequest: SmsRequest): ResponseEntity<SmsDto> {
        val phone = authSmsRequest.phone
        logger.info { "Receive request for sending auth sms to phone $phone" }
        return authService.sendAuthSms(phone)
            .let { ResponseEntity.ok(it) }
    }

    @Operation(summary = "Check auth code",
        description = "return X-Authentication header with jwt token")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200",
                headers = [Header(name = "X-Authorization", description = "Jwt token for authorization",
                required = true)]),
            ApiResponse(description = "There is no user or sms with such phone or id", responseCode = "404"),
            ApiResponse(description = "Sms was expired or wrong code was provided", responseCode = "400")
        ]
    )
    @PutMapping("{smsId}")
    fun checkCode(@Parameter(name = "smsId", `in`= ParameterIn.PATH, description = "sms id in db", required = true,
    allowEmptyValue = false) @PathVariable smsId: Int, @RequestBody checkCodeRequest: CheckCodeRequest): ResponseEntity<SmsDtoWithUser> {
        val code = checkCodeRequest.code
        logger.info { "Receive request for checking code $code for sms id $smsId" }
        return authService.checkCode(smsId, code)
            .let{ ResponseEntity.ok()
                .header("X-Authentication", it.second)
                .body(it.first)
                }
    }
}