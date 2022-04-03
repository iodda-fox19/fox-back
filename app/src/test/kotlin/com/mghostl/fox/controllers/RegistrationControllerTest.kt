package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.auth.CheckCodeRequest
import com.mghostl.fox.auth.SmsRequest
import com.mghostl.fox.config.WithMockFoxUser
import com.mghostl.fox.dto.NewUserData
import com.mghostl.fox.dto.SmsDto
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.sms.services.SmsService
import com.mghostl.fox.utils.user
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.hamcrest.Matchers
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class RegistrationControllerTest: AbstractMvcTest("/api/registration") {
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
    fun `should send sms for new user`() {
        val phone = "89605451954"

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val code = firstArg<String>().split("Registration code: ")[1]
            sendedCode = code

            val phoneArg = secondArg<String>()
            Assertions.assertEquals(phone, phoneArg)
        }
        mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.phone", Matchers.`is`(phone)))
            .andExpect(jsonPath("$.id", Matchers.notNullValue()))
            .andExpect(jsonPath("$.updatedAt", Matchers.notNullValue()))
            .andExpect(jsonPath("$.createdAt", Matchers.notNullValue()))

        val sms = smsRepository.findAll().filter { it.phone == phone }.maxByOrNull { it.createdAt!! }!!
        Assertions.assertEquals(sendedCode, sms.sendedCode)
    }

    @Test
    fun `should return 409 if user has been already registered`() {
        val phone = "89605451594"

        user().apply { this.phone = phone }
            .also { userRepository.save(it) }

        mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isConflict)
    }

    @Test
    fun `should return 403 if sms has been already sent`() {
        val phone = "89605451594"

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            Assertions.assertEquals(phone, phoneArg)
        }

        mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isOk)

        mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isForbidden)
    }

    @Test
    fun `should check code and return auth token`() {
        val phone = "89605451594"

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            Assertions.assertEquals(phone, phoneArg)
            val code = firstArg<String>().split("Registration code: ")[1]
            sendedCode = code
        }
        val response = mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isOk)
            .andGetResponse(SmsDto::class.java)

        mvc.perform(
            MockMvcRequestBuilders.put("$basePath/${response.id}")
            .json(CheckCodeRequest(sendedCode!!)))
            .andExpect(status().isOk)
            .andExpect(MockMvcResultMatchers.header().exists("X-Authentication"))
            .andExpect(jsonPath("$.createdAt", Matchers.notNullValue()))
            .andExpect(jsonPath("$.updatedAt", Matchers.notNullValue()))
    }

    @Test
    fun `should return 409 if there was not sms`() {
        mvc.perform(
            MockMvcRequestBuilders.put("$basePath/123")
            .json(CheckCodeRequest("some code")))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should return 400 if sms was expired`() {
        val phone = "89605451594"

        var sendedCode: String? = null

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            Assertions.assertEquals(phone, phoneArg)
            val code = firstArg<String>().split("Registration code: ")[1]
            sendedCode = code
        }
        val response = mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isOk)
            .andGetResponse(SmsDto::class.java)

        Thread.sleep((2 * 1000 * 60).toLong()) // ждем 2 минуты, когда смска протухнет
        mvc.perform(
            MockMvcRequestBuilders.put("$basePath/${response.id}")
            .json(CheckCodeRequest(sendedCode!!)))
            .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return 400 if code is wrong`() {
        val phone = "89605451594"

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            Assertions.assertEquals(phone, phoneArg)
        }
        val response = mvc.perform(post(basePath).json(SmsRequest(phone)))
            .andExpect(status().isOk)
            .andGetResponse(SmsDto::class.java)

        Thread.sleep((1000 * 10).toLong())
        mvc.perform(
            MockMvcRequestBuilders.put("$basePath/${response.id}")
            .json(CheckCodeRequest("wrong code")))
            .andExpect(status().isBadRequest)
    }

    @WithMockFoxUser(roles = ["USER"], phone = "89605451594")
    @Test
    fun `should return 403 while creating for existing user`() {
        val data = NewUserData("Name")

        mvc.perform(post("$basePath/user")
            .json(data))
            .andExpect(status().isForbidden)
    }

    @WithMockFoxUser(roles = ["NEW_USER"], phone = "89605451594")
    @Test
    fun `should create user`() {
        val data = NewUserData("Name")
        val phone = "89605451594"

        val userDto = UserDto(phone = phone, name = data.name, isAdmin = false,
        isBlocked = false, isDeleted = false, isGamer = false, isTrainer = false,
        )

        val response = mvc.perform(post("$basePath/user")
            .json(data))
            .andExpect(status().isOk)
            .andExpect(header().exists(RegistrationController.X_AUTHENTICATION_HEADER))
            .andGetResponse(UserDto::class.java)

        userDto.id = userRepository.findByPhone(phone = phone)!!.id

        assertEquals(userDto, response)
    }

}