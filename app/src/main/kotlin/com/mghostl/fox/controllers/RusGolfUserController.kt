package com.mghostl.fox.controllers

import com.mghostl.fox.dto.UserRusGolfDTO
import com.mghostl.fox.rusgolf.services.RusGolfUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springdoc.api.annotations.ParameterObject
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/rusgolf")
class RusGolfUserController(
    private val rusGolfUserService: RusGolfUserService
) {

    companion object: KLogging()

    @Operation(summary = "Getting rus golf's user by its id",
    description = "returns UserRusGolfDTO")
    @ApiResponses(
        value = [
            ApiResponse(description = "Successful operation", responseCode = "200"),
            ApiResponse(description = "User is not found", responseCode = "404")
        ]
    )
    @GetMapping
    fun getRusGolfUser(@Parameter(description = "RusGolf user's id",`in` = ParameterIn.QUERY,  name = "golfRegistryIdRU",
    required = true) @RequestParam golfRegistryIdRU: String): ResponseEntity<UserRusGolfDTO> {
        logger.info { "Get rusGolf user with id $golfRegistryIdRU" }
        return ResponseEntity.ok(rusGolfUserService.getUser(golfRegistryIdRU))
            .also {logger.info { "User is $it" }}
    }
}