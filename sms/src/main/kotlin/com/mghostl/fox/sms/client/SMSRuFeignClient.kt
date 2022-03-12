package com.mghostl.fox.sms.client

import com.mghostl.fox.sms.model.SmsResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "SMSRuFeignClient", url = "\${sms-ru.url}")
interface SMSRuFeignClient {

    @GetMapping("send")
    fun sendSms(@RequestParam("api_id") apiId: String, @RequestParam msg: String, @RequestParam json: String = "1",
    @RequestParam("to") phones: List<String>, @RequestParam(required = false) test: String? = null): SmsResponse
}