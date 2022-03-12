package com.mghostl.fox.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.mghostl.fox.AbstractTest
import com.mghostl.fox.sms.client.SMSRuFeignClient
import com.mghostl.fox.sms.model.SmsResponse
import com.mghostl.fox.sms.model.SmsRuException
import com.mghostl.fox.sms.services.SmsService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class SmsServiceTest: AbstractTest() {
    @MockkBean
    lateinit var smsRuFeignClient: SMSRuFeignClient

    @Autowired
    lateinit var smsService: SmsService

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Value("\${sms-ru.api-id}")
    lateinit var apiId: String

    @Test
    fun `should send sms`() {
        val msg = "Test message"
        val phone = "89605451594"
        every { smsRuFeignClient.sendSms(apiId, msg, "1", listOf(phone), "1") } returns smsResponse("sms-success")

        smsService.send(msg, phone)
    }

    @Test
    fun `should throw exception if smth goes wrong`() {
        val msg = "Test message"
        val phone = "89605451594"
        every { smsRuFeignClient.sendSms(apiId, msg, "1", listOf(phone), "1") } returns smsResponse("sms-error")

        assertThrows<SmsRuException> { smsService.send(msg, phone) }
    }

    @Test
    fun `should throw exception if smth goes wrong with sms`() {
        val msg = "Test message"
        val phone = "89605451594"
        every { smsRuFeignClient.sendSms(apiId, msg, "1", listOf(phone), "1") } returns smsResponse("sms-error-sms")

        assertThrows<SmsRuException> { smsService.send(msg, phone) }
    }

    private fun smsResponse(fileName: String) = this.javaClass.getResource("/response/$fileName.json")!!.readText()
        .let { objectMapper.readValue(it, SmsResponse::class.java) }
}