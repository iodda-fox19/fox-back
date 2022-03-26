package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.repository.ClubRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.club
import com.mghostl.fox.utils.user
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class UserControllerTest: AbstractMvcTest("/api/users") {

    companion object {
        const val PHONE = "89605451594"
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var clubRepository: ClubRepository
    
    @AfterEach
    fun afterEach() {
        userRepository.deleteAll()
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return current user`() {

        val user = user()
            .apply {phone = PHONE}
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id!!

        val userDTO = UserDto(id = userId, name = user.name, lastName = user.lastName,
        isGamer = user.isGamer, isTrainer = user.isTrainer, isAdmin = user.isAdmin,
        handicap = user.handicap, golfRegistryIdRU = user.golfRegistryIdRU, homeClub = user.homeClub?.name,
        avatar = user.avatar, isBlocked = false, isDeleted = false, toAddEventsInCalendar = false)
        mvc.perform(get(basePath))
            .andExpect(status().isOk)
            .andExpectJson(userDTO)
    }

    @Test
    fun `should return 403 if not authenticated`() {
        mvc.perform(get(basePath))
            .andExpect(status().isForbidden)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should return 404 if there is no such user`() {
        mvc.perform(get(basePath))
            .andExpect(status().isNotFound)
    }
}