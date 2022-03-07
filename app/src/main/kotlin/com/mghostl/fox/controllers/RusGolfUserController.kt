package com.mghostl.fox.controllers

import com.mghostl.fox.dto.UserRusGolfDTO
import com.mghostl.fox.rusgolf.services.RusGolfUserService
import mu.KLogging
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

    @GetMapping
    fun getRusGolfUser(@RequestParam golfRegistryIdRU: String): ResponseEntity<UserRusGolfDTO> {
        logger.info { "Get rusGolf user with id $golfRegistryIdRU" }
        return ResponseEntity.ok(rusGolfUserService.getUser(golfRegistryIdRU))
            .also {logger.info { "User is $it" }}
    }
}