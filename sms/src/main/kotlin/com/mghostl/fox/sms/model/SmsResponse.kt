package com.mghostl.fox.sms.model

import com.fasterxml.jackson.annotation.JsonProperty

data class SmsResponse(
    val status: Status,
    @JsonProperty("status_code")
    val statusCode: Int,
    val sms: Map<String, SingleSmsResponse>,
    val balance: String
)

data class SingleSmsResponse(
    val status: Status,
    @JsonProperty("status_code")
    val statusCode: Int,
    @JsonProperty("sms_id")
    val smsId: String?,
    @JsonProperty("status_text")
    val statusText: String?
)

enum class Status {
    OK, ERROR
}