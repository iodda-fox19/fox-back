package com.mghostl.fox.sms.services

import com.mghostl.fox.sms.client.SMSRuFeignClient
import com.mghostl.fox.sms.model.SmsRuException
import com.mghostl.fox.sms.model.Status
import mu.KLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SmsServiceImpl(
    private val smsRuFeignClient: SMSRuFeignClient
) : SmsService {
    @Value("\${sms-ru.api-id}")
    lateinit var apiId: String

    @Value("\${sms-ru.test}")
    lateinit var test: String

    companion object : KLogging()

    override fun send(msg: String, phone: String) {
        logger.info { "Sending sms with message $msg to $phone " }
        smsRuFeignClient.sendSms(apiId, msg, "1", listOf(phone), test(), "FOX 19")
            .also { logger.info { "Sms was sent with status ${it.status}" } }
            .also {
                if (it.status != Status.OK) {
                    throw SmsRuException("Received error from sms ru: $it")
                }
            }
            .also {
                val singleSmsResponse = it.sms[phone]!!
                if(singleSmsResponse.status != Status.OK) {
                    throw SmsRuException("Received error from sms ru: ${singleSmsResponse.statusText}")
                }
            }
    }

    private fun test() = if (isTest()) "1" else null

    private fun isTest() = test == "true"
}