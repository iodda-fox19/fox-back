package com.mghostl.fox.services

import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.PatchUserRequest
import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO

interface UserService {
    fun updateUser(userDto: UserDto, phone: String): UserDto
    fun findByGolfRegistryId(golfRegistryId: String): User?
    fun findByPhone(phone: String): UserDto
    fun findById(id: Int): User
    fun deleteUser(phone: String)
    fun patchUser(userId: Int, patchUserRequest: PatchUserRequest): UserDto
}