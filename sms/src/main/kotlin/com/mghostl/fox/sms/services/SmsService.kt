package com.mghostl.fox.sms.services

interface SmsService {
    fun send(msg: String, phone: String)
}