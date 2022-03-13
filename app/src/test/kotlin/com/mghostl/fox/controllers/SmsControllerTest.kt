package com.mghostl.fox.controllers

import com.mghostl.fox.AbstractMvcTest
import com.mghostl.fox.model.User
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.sms.services.SmsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class SmsControllerTest: AbstractMvcTest("/api/sms") {

    @MockkBean
    lateinit var smsService: SmsService

    @Autowired
    lateinit var smsRepository: SmsRepository

    @Autowired
    lateinit var userRepository: UserRepository

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
        mvc.perform(post(basePath)
            .param("phone", phone))
            .andExpect(status().isOk)

        val sms = smsRepository.findAll().filter { it.phone == phone }.maxByOrNull { it.createdAt!! }!!
        assertEquals(sendedCode, sms.sendedCode)
    }

    @Test
    fun `should return 409 if there is no user with such phone`() {
        val phone = "89605451594"

        mvc.perform(post(basePath)
            .param("phone", phone))
            .andExpect(status().isConflict)
    }

    @Test
    fun `should return 403 if sms has been already sent`() {
        val phone = "89605451594"
        userRepository.save(User(phone = phone))

        every { smsService.send(any(), any()) } answers {
            val phoneArg = secondArg<String>()
            assertEquals(phone, phoneArg)
        }

        mvc.perform(post(basePath)
            .param("phone", phone))
            .andExpect(status().isOk)

        mvc.perform(post(basePath)
            .param("phone", phone))
            .andExpect(status().isForbidden)
    }
}