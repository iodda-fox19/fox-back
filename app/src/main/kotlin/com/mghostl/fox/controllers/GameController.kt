package com.mghostl.fox.controllers

import com.mghostl.fox.dto.GetGamesResponse
import com.mghostl.fox.model.GameFilter
import com.mghostl.fox.resolvers.UserPhone
import com.mghostl.fox.services.GameService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.annotation.security.RolesAllowed

@RequestMapping("api/games")
@RestController
@RolesAllowed("USER")
class GameController(
    private val gameService: GameService
) {

    companion object: KLogging()

    @GetMapping
    @Operation(summary = "Get games")
    @ApiResponses(
        ApiResponse(description = "successfully getting games", responseCode = "200")
    )
    fun getGames(@UserPhone phone: String, @RequestParam(required = false, defaultValue = "10") limit: Int, @RequestParam(required = false, defaultValue = "0") offset: Int,
    @RequestBody(required = false) filter: GameFilter? = null
    ): ResponseEntity<GetGamesResponse> {
        logger.info { "Getting games for user $phone" }
        return (filter
            ?.let{gameService.getGames(filter, limit, offset)}
            ?: gameService.getGames(phone, limit, offset))
            .let { ResponseEntity.ok(it) }
    }
}