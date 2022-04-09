package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.repository.ComplaintRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.user
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ComplaintControllerTest: AbstractMvcTest("/api/complaints") {
    @Autowired
    private lateinit var complaintRepository: ComplaintRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun before() {
        complaintRepository.deleteAll()
        userRepository.deleteAll()
    }

    @WithMockFoxUser(phone = "89605451594", roles = ["USER"])
    @Test
    fun `should create complaint`() {
        val phone = "89605451594"
        val userId = user()
            .apply { this.phone = phone }
            .also { userRepository.save(it) }.id

        val indictedUserId = user()
            .also { userRepository.save(it) }.id

        val complaintDTO = ComplaintDTO(indictedUserId, "Comment")

        mvc.perform(post(basePath).json(complaintDTO))
            .andExpect(status().isOk)
            .andExpectJson(complaintDTO)

        val complaint = complaintRepository.findAll()
                .first { it.userId == userId && it.indictedUserId == indictedUserId }
        assertEquals(complaintDTO.comment, complaint.comment)
    }

    @Test
    fun `should return 403 if user's not auth`() {
        mvc.perform(post(basePath).json(ComplaintDTO(123, "comment")))
            .andExpect(status().isForbidden)
    }

    @WithMockFoxUser(phone = "89605451594", roles = ["USER"])
    @Test
    fun `should return 400 if there is no comment in complaint`() {
        user().apply { this.phone = "89605451594" }
            .also { userRepository.save(it) }
        mvc.perform(post(basePath).json(ComplaintDTO()))
            .andExpect(status().isBadRequest)
    }

    @WithMockFoxUser(phone = "89605451594", roles = ["USER"])
    @Test
    fun `should return 404 if indicted user doesn't exists`() {
        user().apply { this.phone = "89605451594" }
            .also { userRepository.save(it) }
        mvc.perform(post(basePath).json(ComplaintDTO(indictedUserId = 123, comment = "Comment")))
            .andExpect(status().isNotFound)
    }
}