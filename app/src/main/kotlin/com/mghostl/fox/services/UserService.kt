package com.mghostl.fox.services

import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.UserDTO

interface UserService {
    /**
        @param user - существующий user из базы
        @param userDTO - данные из русгольфа
        @return user вернувшийся после сохранения в базу
     */
    fun updateUser(user: User, userDTO: UserDTO): User
    fun findByGolfRegistryId(golfRegistryId: String): User?
}