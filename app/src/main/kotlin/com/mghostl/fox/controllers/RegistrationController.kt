package com.mghostl.fox.controllers

import com.mghostl.fox.auth.SmsRequest
import com.mghostl.fox.auth.CheckCodeRequest
import com.mghostl.fox.dto.NewUserData
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.SmsDtoWithUser
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.RegistrationService
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
import javax.annotation.security.RolesAllowed

@RestController
@RequestMapping("api/registration")
class RegistrationController(
    private val registrationService: RegistrationService
) {

    companion object: KLogging() {
        const val X_AUTHENTICATION_HEADER = "X-Authentication"
    }



    @Operation(summary = "Sending sms to registration user",
        description = "sending to the phone via sms ru")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200"),
            ApiResponse(description = "Sms has already been send", responseCode = "403"),
            ApiResponse(description = "User has been already registered", responseCode = "409")
        ]
    )
    @PostMapping
    fun sendSms(@RequestBody smsRequest: SmsRequest): ResponseEntity<SmsDto> {
        val phone = smsRequest.phone
        logger.info { "Sending sms to phone: $phone" }
        return registrationService.sendSms(phone)
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
        logger.info { "Checking code $checkCodeRequest for sms with id $smsId" }
        return registrationService.checkCode(smsId, checkCodeRequest.code)
            .let{ ResponseEntity.ok()
            .header(X_AUTHENTICATION_HEADER, it.second)
            .body(it.first)
        }
    }

    @RolesAllowed("NEW_USER")
    @PostMapping("user")
    @Operation(summary = "Creating new user")
    @ApiResponses(
        value = [ ApiResponse(description = "User was registered", responseCode = "200",
            headers = [Header(name = "X-Authorization", description = "Jwt token for authorization",
                required = true)]),
            ApiResponse(description = "User has been already registered", responseCode = "409")]
    )
    fun createUser(@UserPhone phone: String, @RequestBody data: NewUserData): ResponseEntity<UserDto> {
        logger.info { "Creating new user with phone $phone and data: $data" }
        return registrationService.createUser(phone, data)
            .let { ResponseEntity.ok()
                .header(X_AUTHENTICATION_HEADER, it.second)
                .body(it.first)}
    }
}