package com.mghostl.fox.rusgolf.services

import com.mghostl.fox.dto.UserRusGolfDTO
import com.mghostl.fox.model.UserRusGolf

interface RusGolfUserService {
    fun save(userRusGolf: UserRusGolf): UserRusGolf
    fun getUser(golfRegistryIdRU: String): UserRusGolfDTO
}