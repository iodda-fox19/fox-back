package com.mghostl.fox.services

import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO

interface UserService {
    /**
        @param user - существующий user из базы
        @param userDTO - данные из русгольфа
        @return user вернувшийся после сохранения в базу
     */
    fun updateUser(user: User, userDTO: RusGolfUserDTO): User
    fun findByGolfRegistryId(golfRegistryId: String): User?
    fun findByPhone(phone: String): UserDto
    fun findById(id: Int): User
}