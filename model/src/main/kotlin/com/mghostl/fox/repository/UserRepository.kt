package com.mghostl.fox.repository

import com.mghostl.fox.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Int> {
    fun findByGolfRegistryIdRU(golfRegistryIdRu: String): User?
    fun findByPhone(phone: String): User?
}