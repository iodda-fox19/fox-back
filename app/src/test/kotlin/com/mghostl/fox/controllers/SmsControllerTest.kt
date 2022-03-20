package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.auth.AuthSmsRequest
import com.mghostl.fox.auth.CheckCodeRequest
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.model.User
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.sms.services.SmsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.hamcrest.core.Is
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SmsControllerTest: AbstractMvcTest("/api/sms") {

    @MockkBean
    lateinit var smsService: SmsService

    @Autowired
    lateinit var smsRepository: SmsRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @BeforeEach
    fun clean() {
        userRepository.deleteAll()
        smsRepository.deleteAll()

    }

    @Test
    fun `should send sms`() {
        val phone = "89605451954"
        userRepository.save(User(phone = phone))

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val code = firstArg<String>().split("Authentication code: ")[1]
            sendedCode = code

            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
        }
        mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.phone", `is`(phone)))
            .andExpect(jsonPath("$.id", notNullValue()))
            .andExpect(jsonPath("$.updatedAt", notNullValue()))
            .andExpect(jsonPath("$.createdAt", notNullValue()))

        val sms = smsRepository.findAll().filter { it.phone == phone }.maxByOrNull { it.createdAt!! }!!
        assertEquals(sendedCode, sms.sendedCode)
    }

    @Test
    fun `should return 409 if there is no user with such phone`() {
        val phone = "89605451594"

        mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 403 if sms has been already sent`() {
        val phone = "89605451594"
        userRepository.save(User(phone = phone))

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
        }

        mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isOk)

        mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isForbidden)
    }

    @Test
    fun `should check code and return auth token`() {
        val phone = "89605451594"
        userRepository.save(User(phone = phone))

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
            val code = firstArg<String>().split("Authentication code: ")[1]
            sendedCode = code
        }
       val response = mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isOk)
           .andGetResponse(SmsDto::class.java)

        mvc.perform(put("$basePath/${response.id}")
            .json(CheckCodeRequest(sendedCode!!)))
            .andExpect(status().isOk)
            .andExpect(header().exists("X-Authentication"))
    }

    @Test
    fun `should return 409 if there was not sms`() {
        mvc.perform(put("$basePath/123")
            .json(CheckCodeRequest("some code")))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 400 if sms was expired`() {
        val phone = "89605451594"
        userRepository.save(User(phone = phone))

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
            val code = firstArg<String>().split("Authentication code: ")[1]
            sendedCode = code
        }
        val response = mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isOk)
            .andGetResponse(SmsDto::class.java)

        Thread.sleep((2 * 1000 * 60).toLong()) // ждем 2 минуты, когда смска протухнет
        mvc.perform(put("$basePath/${response.id}")
            .json(CheckCodeRequest(sendedCode!!)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 if code is wrong`() {
        val phone = "89605451594"
        userRepository.save(User(phone = phone))

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
        }
        val response = mvc.perform(post(basePath).json(AuthSmsRequest(phone)))
            .andExpect(status().isOk)
            .andGetResponse(SmsDto::class.java)

        Thread.sleep((1000 * 10).toLong())
        mvc.perform(put("$basePath/${response.id}")
            .json(CheckCodeRequest("wrong code")))
            .andExpect(status().isBadRequest)
    }
}