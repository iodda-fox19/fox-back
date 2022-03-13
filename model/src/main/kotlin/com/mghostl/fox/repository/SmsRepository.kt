package com.mghostl.fox.repository

import com.mghostl.fox.model.Sms
import org.springframework.data.jpa.repository.JpaRepository

interface SmsRepository: JpaRepository<Sms, Int> {
    fun findAllByPhone(phone: String): Set<Sms>
}