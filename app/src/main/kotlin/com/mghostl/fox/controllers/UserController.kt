package com.mghostl.fox.controllers

import com.mghostl.fox.dto.ForeignUserDto
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.mappers.UserMapper
import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.model.PatchUserRequest
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RequestMapping("api/users")
@RestController
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    companion object : KLogging()

    @RolesAllowed("USER")
    @GetMapping
    @Operation(summary = "get current user")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200")
        ]
    )
    fun getUser(@UserPhone phone: String): ResponseEntity<UserDto> =
        SecurityContextHolder.getContext().authentication.principal
            .let { it as FoxUserDetails }.username!!
            .also { logger.info { "Getting current user with phone: $it" } }
            .let { userService.findByPhone(phone) }
            .let { ResponseEntity.ok(it) }

    @RolesAllowed("USER")
    @GetMapping("{userId}")
    @Operation(summary = "get user by id")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200"),
            ApiResponse(description = "User was not found", responseCode = "404")
        ]
    )
    fun getUser(@PathVariable userId: Int): ResponseEntity<ForeignUserDto> {
        logger.info { "Getting user by id $userId" }
        return userService.findById(userId)
            .let { userMapper.mapToForeignUser(it) }
            .let { ResponseEntity.ok(it) }
    }

    @RolesAllowed("USER")
    @PutMapping
    @Operation(summary = "update current user")
    @ApiResponses(
        value = [
            ApiResponse(
                description = "User was updated", responseCode = "200", content = [Content(
                    mediaType = "application/json", schema = Schema(implementation = UserDto::class)
                )]
            ),
            ApiResponse(description = "Forbidden kind of update", responseCode = "409")
        ]
    )
    fun updateUser(@RequestBody userDto: UserDto, @UserPhone phone: String): ResponseEntity<UserDto> {
        logger.info { "Updating current user $phone with dto $userDto" }
        return userService.updateUser(userDto, phone)
            .let { ResponseEntity.ok(it) }
    }

    @RolesAllowed("ADMIN")
    @PatchMapping("{userId}")
    @Operation(summary = "patch foreign user")
    @ApiResponses(
        value = [
            ApiResponse(description = "user was patched", responseCode = "200"),
            ApiResponse(description = "User wasn't found", responseCode = "404")
        ]
    )
    fun patchUser(@PathVariable userId: Int, @RequestBody patchUserRequest: PatchUserRequest): ResponseEntity<UserDto> {
        logger.info { "Patching by admin user with $userId" }
        return userService.patchUser(userId, patchUserRequest)
            .let { ResponseEntity.ok(it) }
    }

    @RolesAllowed("USER")
    @DeleteMapping
    @Operation(summary = "Delete current user")
    @ApiResponses(
        value = [
            ApiResponse(description = "User was deleted", responseCode = "204")
        ]
    )
    fun deleteUser(@UserPhone phone: String): ResponseEntity<Unit> {
        logger.info { "Deleting current user $phone" }
        userService.deleteUser(phone)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}