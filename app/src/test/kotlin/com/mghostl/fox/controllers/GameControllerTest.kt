package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.GameDTO
import com.mghostl.fox.dto.GetGamesResponse
import com.mghostl.fox.mappers.UserMapper
import com.mghostl.fox.model.GameFilter
import com.mghostl.fox.repository.GameRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.game
import com.mghostl.fox.utils.user
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class GameControllerTest: AbstractMvcTest("/api/games") {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun before() {
        userRepository.deleteAll()
        gameRepository.deleteAll()
    }

    @Test
    fun `should return 403 if user is not auth`() {
        mvc.perform(get(basePath))
            .andExpect(status().isForbidden)
    }

    @WithMockFoxUser(["USER"], phone = "89605451594")
    @Test
    fun `should return games for current user`() {
        val user = user().apply { phone = "89605451594" }
            .let{ userRepository.save(it) }

        val game = game().apply { users.add(user)}
            .let { gameRepository.save(it) }

        mvc.perform(get(basePath))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.count", `is`(1)))
            .andExpect(jsonPath("$.data[0].id", `is`(game.id)))
            .andExpect(jsonPath("$.data[0].clubId", `is`(game.clubId)))
            .andExpect(jsonPath("$.data[0].date", `is`(game.date.toString())))
            .andExpect(jsonPath("$.data[0].holes", `is`(game.holes)))
            .andExpect(jsonPath("$.data[0].gamersCount", `is`(game.gamersCount)))
            .andExpect(jsonPath("$.data[0].reserved", `is`(game.reserved)))
            .andExpect(jsonPath("$.data[0].time", `is`(DateTimeFormatter.ofPattern("HH:mm").format(game.time))))
            .andExpect(jsonPath("$.data[0].description", `is`(game.description)))
            .andExpect(jsonPath("$.data[0].name", `is`(game.name)))
            .andExpect(jsonPath("$.data[0].onlyMembers", `is`(game.onlyMembers)))
    }

    @WithMockFoxUser(["USER"], "89605451954")
    @Test
    fun `should page games for user`() {
        val user = user().apply { phone = "89605451954" }
            .let{ userRepository.save(it) }

        val firstGameId = game().apply { users.add(user) }
            .also { gameRepository.save(it) }
            .id!!

        repeat(10) {
            game().apply { users.add(user) }
                .also { gameRepository.save(it) }
        }
        val games = mutableSetOf<GameDTO>()
        repeat(10) {
            games.add(GameDTO(it + firstGameId, setOf(userMapper.map(user)), 1, 1,LocalDate.now(), 3, 4, false, BigDecimal.valueOf(1000),
            BigDecimal.valueOf(2000), LocalTime.MIDNIGHT, "Big game", "Big big name", false, 1.5f, 53f))
        }

        mvc.perform(get(basePath))
            .andExpect(status().isOk)
            .andExpectJson(GetGamesResponse(games, 11))
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by clubId`() {
        repeat(10) {
            game().apply { clubId = Random.nextInt() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(clubId = 1)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertEquals(1, it.clubId)
        }
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by countryId`() {
        repeat(10) {
            game().apply { this.countryId = Random.nextInt() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(countryId = 1)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertEquals(1, it.countryId)
        }
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by numOfHoles`() {
        repeat(10) {
            game().apply { this.holes = Random.nextInt() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(numOfHoles = 1)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertEquals(1, it.holes)
        }
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by num of players`() {
        repeat(10) {
            game().apply { this.gamersCount = Random.nextInt() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(gamersCount = 1)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertEquals(1, it.gamersCount)
        }
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by allowed only for members`() {
        repeat(10) {
            game().apply { this.onlyMembers = Random.nextBoolean() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(onlyForMembers = true)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertTrue( it.onlyMembers!!)
        }
    }

    @WithMockFoxUser(["USER"])
    @Test
    fun `should filter games by handicap`() {
        repeat(10) {
            game().apply { this.handicapMax = Random.nextFloat() }
                .apply { this.handicapMin = Random.nextFloat() }
                .also { gameRepository.save(it) }
        }

        val games = mvc.perform(get(basePath)
            .json(GameFilter(handicapMin = 1f, handicapMax = 53f)))
            .andExpect(status().isOk)
            .andGetResponse(GetGamesResponse::class.java)

        games.data.forEach {
            assertTrue(it.handicapMax!! < 53f)
            assertTrue(it.handicapMin!! > 1f)
        }
    }
}