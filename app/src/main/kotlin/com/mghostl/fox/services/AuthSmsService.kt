package com.mghostl.fox.services

interface AuthSmsService {
    fun auth(phone: String, userId: Int)

}