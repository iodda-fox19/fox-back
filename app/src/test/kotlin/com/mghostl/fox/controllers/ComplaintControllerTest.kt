package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.ComplaintDTO
import com.mghostl.fox.dto.GetComplaintsResponse
import com.mghostl.fox.model.Complaint
import com.mghostl.fox.repository.ComplaintRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.user
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
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
        assertFalse(complaint.resolved)
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

    @WithMockFoxUser(roles = ["ADMIN"])
    @Test
    fun `should return unresolved complaints`() {
        val resolvedComplaint = Complaint(userId = 1, indictedUserId = 2, resolved = true, comment = "Comment")
            .also { complaintRepository.save(it) }
        val unResolvedComplaint = Complaint(userId = 1, indictedUserId = 2, resolved = false, comment = "Comment 2")
            .also { complaintRepository.save(it) }

        mvc.perform(get(basePath))
            .andExpect(status().isOk)
            .andExpectJson(GetComplaintsResponse(setOf(ComplaintDTO(unResolvedComplaint.indictedUserId, unResolvedComplaint.comment)), 1))
    }

    @WithMockFoxUser(roles = ["ADMIN"])
    @Test
    fun `getting complaints should return pageable response`() {
        repeat(20) { i ->
            Complaint(userId = 1, indictedUserId = 2, resolved = false, comment = "Comment $i")
                .also { complaintRepository.save(it) }
        }
        val limit = 10

        val expectedResult = mutableSetOf<ComplaintDTO>()
        repeat(10) { i ->
            ComplaintDTO(2, "Comment $i")
                .also { expectedResult.add(it) }
        }
        val result = mvc.perform(get(basePath)
            .param("limit", limit)
            .param("offset", 0)
        )
            .andExpect(status().isOk)
            .andExpectJson(GetComplaintsResponse(expectedResult, 20))
            .andGetResponse(GetComplaintsResponse::class.java)

        assertEquals(limit, result.complaints.size)
    }

    @WithMockFoxUser(roles = ["USER"])
    @Test
    fun `should return 403 for not admin`() {
        mvc.perform(get(basePath))
            .andExpect(status().isForbidden)
    }
}