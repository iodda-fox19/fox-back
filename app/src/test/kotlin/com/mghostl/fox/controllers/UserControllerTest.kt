package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.ForeignUserDto
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.Avatar
import com.mghostl.fox.model.Club
import com.mghostl.fox.model.PatchUserRequest
import com.mghostl.fox.repository.ClubRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.user
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DirtiesContext
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
        avatar = user.avatar, isBlocked = false, isDeleted = false, toAddEventsInCalendar = false, phone = PHONE)

        mvc.perform(get(basePath))
            .andExpect(status().isOk)
            .andExpectJson(userDTO)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return foreign user`() {
        val user = user().apply{phone = PHONE}
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id

        val foreignUserDto = ForeignUserDto(id = userId, name = user.name, lastName = user.lastName,
            isGamer = user.isGamer, isTrainer = user.isTrainer, isAdmin = user.isAdmin,
            handicap = user.handicap, golfRegistryIdRU = user.golfRegistryIdRU, homeClub = user.homeClub?.name,
            avatar = user.avatar, isBlocked = false)

        mvc.perform(get("$basePath/$userId"))
            .andExpect(status().isOk)
            .andExpectJson(foreignUserDto)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return 404 if not found foreign user`() {
       val userId = 1

        mvc.perform(get("$basePath/$userId"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 403 for getting foreign user  if not authenticated`() {
        mvc.perform(get("$basePath/123"))
            .andExpect(status().isForbidden)
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

    @DirtiesContext
    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should update current user`() {
        val user = user()
            .apply {phone = PHONE}
            .also { userRepository.save(it) }

        val savedUser = userRepository.findByPhone(PHONE)
        val userId = savedUser!!.id!!
        val newClub = Club(name = "new Name")
            .also { clubRepository.save(it) }


        val userDTO = UserDto(id = userId, name = user.name + "1", lastName = user.lastName + "1",
            isGamer = user.isGamer, isTrainer = user.isTrainer?.let { !it }, isAdmin = user.isAdmin?.let { !it },
            handicap = user.handicap!! + 1, golfRegistryIdRU = user.golfRegistryIdRU + 1, homeClub = newClub.name,
            avatar = Avatar("new", "new url"), isBlocked = !user.isBlocked, isDeleted = !user.isDeleted,
            toAddEventsInCalendar = user.toAddEventsInCalendar?.let { !it } ?: false,
            phone = PHONE)

        val result = UserDto(id = userId, avatar = userDTO.avatar, name = userDTO.name, lastName = userDTO.lastName, isGamer = userDTO.isGamer,
        isTrainer = userDTO.isTrainer, isAdmin = userDTO.isAdmin, isSubmittedAdministrator = user.isSubmittedAdministrator, isSubmittedTrainer = user.isSubmittedTrainer,
        handicap = userDTO.handicap, isSubmittedHandicap = if(userDTO.handicap == user.handicap) user.isSubmittedHandicap else false,
        golfRegistryIdRU = userDTO.golfRegistryIdRU, homeClub = userDTO.homeClub, phone = user.phone, toAddEventsInCalendar = userDTO.toAddEventsInCalendar,
        isDeleted = user.isDeleted, isBlocked = user.isBlocked)

        mvc.perform(put(basePath).json(userDTO))
            .andExpect(status().isOk)
            .andExpectJson(result)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should return 409 if try to update phone by not a trainer`() {
        val user = user()
            .apply {isTrainer = false}
            .apply {phone = PHONE}
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id!!

        val userDTO = UserDto(id = userId, name = user.name + "1", lastName = user.lastName + "1",
            isGamer = user.isGamer?.let { !it }, isTrainer = user.isTrainer?.let { !it }, isAdmin = user.isAdmin?.let { !it },
            handicap = user.handicap!! + 1, golfRegistryIdRU = user.golfRegistryIdRU + 1, homeClub = user.homeClub?.name,
            avatar = Avatar("new", "new url"), isBlocked = !user.isBlocked, isDeleted = !user.isDeleted,
            toAddEventsInCalendar = user.toAddEventsInCalendar?.let { !it } ?: false,
            phone = PHONE + 1)

        mvc.perform(put(basePath).json(userDTO))
            .andExpect(status().isConflict)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should  update phone by trainer`() {
        val user = user()
            .apply {isTrainer = true}
            .apply {isSubmittedTrainer = true}
            .apply {phone = PHONE}
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id!!

        val userDTO = UserDto(id = userId, name = user.name + "1", lastName = user.lastName + "1",
            isGamer = user.isGamer?.let { !it }, isTrainer = user.isTrainer?.let { !it }, isAdmin = user.isAdmin?.let { !it },
            handicap = user.handicap!! + 1, golfRegistryIdRU = user.golfRegistryIdRU + 1, homeClub = user.homeClub?.name,
            avatar = Avatar("new", "new url"), isBlocked = !user.isBlocked, isDeleted = !user.isDeleted,
            toAddEventsInCalendar = user.toAddEventsInCalendar?.let { !it } ?: false,
            phone = PHONE + 1)

        val result = UserDto(id = userId, avatar = userDTO.avatar, name = userDTO.name, lastName = userDTO.lastName, isGamer = userDTO.isGamer,
            isTrainer = userDTO.isTrainer, isAdmin = userDTO.isAdmin, isSubmittedAdministrator = user.isSubmittedAdministrator, isSubmittedTrainer = user.isSubmittedTrainer,
            handicap = userDTO.handicap, isSubmittedHandicap = if(userDTO.handicap == user.handicap) user.isSubmittedHandicap else false,
            golfRegistryIdRU = userDTO.golfRegistryIdRU, homeClub = userDTO.homeClub, phone = userDTO.phone, toAddEventsInCalendar = userDTO.toAddEventsInCalendar,
            isDeleted = user.isDeleted, isBlocked = user.isBlocked)

        mvc.perform(put(basePath).json(userDTO))
            .andExpect(status().isOk)
            .andExpectJson(result)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should delete fields for current user`() {
        val user = user()
            .apply {phone = PHONE}
            .apply { isSubmittedTrainer = true  }
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id!!

        val userDTO = UserDto(id = userId, name = user.name, lastName = null,
            isGamer = null, isTrainer = null, isAdmin = null,
            handicap = user.handicap, golfRegistryIdRU = null, homeClub = null,
            avatar = null, isBlocked = user.isBlocked, isDeleted = user.isDeleted,
            toAddEventsInCalendar = user.toAddEventsInCalendar,
            phone = null)

        val result = UserDto(id = userId, avatar = null, name = user.name, lastName = null, isGamer = null,
            isTrainer = null, isAdmin = null, isSubmittedAdministrator = user.isSubmittedAdministrator, isSubmittedTrainer = user.isSubmittedTrainer,
            handicap = user.handicap, isSubmittedHandicap = user.isSubmittedHandicap,
            golfRegistryIdRU = null, homeClub = null, phone = null, toAddEventsInCalendar = user.toAddEventsInCalendar,
            isDeleted = user.isDeleted, isBlocked = user.isBlocked)

        mvc.perform(put(basePath).json(userDTO))
            .andExpect(status().isOk)
            .andExpectJson(result)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should not delete gamer status with sumbitted another one for current user`() {
        val user = user()
            .apply {phone = PHONE}
            .apply { isSubmittedTrainer = false  }
            .apply { isSubmittedAdministrator = false  }
            .also { userRepository.save(it) }

        val userId = userRepository.findByPhone(PHONE)!!.id!!

        val userDTO = UserDto(id = userId, name = user.name, lastName = null,
            isGamer = null, isTrainer = null, isAdmin = null,
            handicap = user.handicap, golfRegistryIdRU = null, homeClub = null,
            avatar = null, isBlocked = user.isBlocked, isDeleted = user.isDeleted,
            toAddEventsInCalendar = user.toAddEventsInCalendar,
            phone = null)

        mvc.perform(put(basePath).json(userDTO))
            .andExpect(status().isConflict)
    }

    @WithMockFoxUser(roles = ["User"], phone = PHONE)
    @Test
    fun `should delete current user`() {
        user().apply { isDeleted = false }
            .apply { phone = PHONE }
            .also { userRepository.save(it)}

        mvc.perform(delete(basePath))
            .andExpect(status().isNoContent)

        assertTrue(userRepository.findByPhone(PHONE)!!.isDeleted)
    }

    @WithMockFoxUser(roles = ["ADMIN"], phone = PHONE)
    @Test
    fun `Admin should update other user`() {
        val userId = user()
            .apply {
                isSubmittedTrainer = false
                isSubmittedAdministrator = false
                isBlocked = false
                isDeleted = false
            }
            .also { userRepository.save(it) }.id!!

        val patchRequest = PatchUserRequest(
            isSubmittedTrainer = true,
            isSubmittedAdmin = true,
            isBlocked = true,
            isDeleted = true
        )

        mvc.perform(patch("$basePath/$userId").json(patchRequest))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.isSubmittedTrainer", `is`(patchRequest.isSubmittedTrainer)))
            .andExpect(jsonPath("$.isSubmittedAdministrator", `is`(patchRequest.isSubmittedAdmin)))
            .andExpect(jsonPath("$.isBlocked", `is`(patchRequest.isBlocked)))
            .andExpect(jsonPath("$.isDeleted", `is`(patchRequest.isDeleted)))
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `user should not update foreign user`() {
        val userId = user()
            .apply {
                isSubmittedTrainer = false
                isSubmittedAdministrator = false
                isBlocked = false
                isDeleted = false
            }
            .also { userRepository.save(it) }.id!!

        val patchRequest = PatchUserRequest(
            isSubmittedTrainer = true,
            isSubmittedAdmin = true,
            isBlocked = true,
            isDeleted = true
        )

        mvc.perform(patch("$basePath/$userId").json(patchRequest))
            .andExpect(status().isForbidden)
    }

}