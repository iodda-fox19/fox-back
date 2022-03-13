package com.mghostl.fox.services

interface AuthService {
    fun sendAuthSms(phone: String)
}