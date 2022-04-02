package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.UploadFileResponse
import com.mghostl.fox.model.Avatar
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.user
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.contains
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.Resource
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class AvatarControllerTest: AbstractMvcTest("/api/avatars") {
    companion object {
        const val PHONE = "89605451594"
    }

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun afterEach() {
        userRepository.deleteAll()
    }

    @Test
    fun `should return 400 if user is not authorized`() {
        val file = MockMultipartFile("file", "Filename.png", "application/octet-stream", "file".toByteArray())
        mvc.perform(
            multipart(basePath)
                .file(file))
            .andExpect(status().isForbidden)
    }

    @Test
    fun `should return 400 if there is no file`() {
        mvc.perform(multipart(basePath))
            .andExpect(status().isBadRequest)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should upload file`() {
        val userId = user().apply{phone = UserControllerTest.PHONE }
            .also { userRepository.save(it) }.id!!

        val file = MockMultipartFile("file", "Filename.png", "application/octet-stream", "file".toByteArray())

        mvc.perform(multipart(basePath).file(file))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.avatar.fileName", `is`(file.originalFilename)))
            .andExpect(jsonPath("$.avatar.url", matchesPattern(".*$basePath.*\\?userId=$userId")))
    }

    @Test
    fun `should return 400 if there is no userId`() {
        mvc.perform(get("$basePath/file"))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 405 if there is no filePath`() {
        mvc.perform(get(basePath))
            .andExpect(status().isMethodNotAllowed)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return 404 if there is no such user`() {
        mvc.perform(get("$basePath/file")
            .param("userId", 1))
            .andExpect(status().isNotFound)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return 404 if there is no such file`() {
        val userId = user().apply { phone = PHONE }.also { userRepository.save(it) }.id!!
        mvc.perform(get("$basePath/file.png")
            .param("userId", userId))
            .andExpect(status().isNotFound)
    }

    @WithMockFoxUser(roles = ["USER"], phone = PHONE)
    @Test
    fun `should return photo for current user`() {
        user().apply { phone = PHONE }.also { userRepository.save(it) }.id!!

        val file = MockMultipartFile("file", "Filename.png", "application/octet-stream", "file".toByteArray())

        val avatar = mvc.perform(multipart(basePath).file(file))
            .andExpect(status().isOk)
            .andGetResponse(UploadFileResponse::class.java).avatar

       val resource =  mvc.perform(get(avatar.url))
            .andExpect(status().isOk)
           .andReturn().response.contentAsByteArray

        resource.forEach { assertTrue(file.bytes.contains(it)) }
        file.bytes.forEach { assertTrue(resource.contains(it)) }


    }

}