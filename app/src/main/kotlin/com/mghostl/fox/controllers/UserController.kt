package com.mghostl.fox.controllers

import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RolesAllowed("USER")
@RequestMapping("api/users")
@RestController
class UserController(
    private val userService: UserService
) {

    companion object: KLogging()

    @GetMapping
    @Operation(summary = "get current user")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200")
        ]
    )
    fun getUser(): ResponseEntity<UserDto> = SecurityContextHolder.getContext().authentication.principal
    .let { it as FoxUserDetails }.username!!
        .also { logger.info { "Getting current user with phone: $it" } }
        .let { userService.findByPhone(it) }
        .let { ResponseEntity.ok(it) }
}