package com.mghostl.fox.mappers

import com.mghostl.fox.dto.UserRusGolfDTO
import com.mghostl.fox.model.UserRusGolf
import org.mapstruct.Mapper

@Mapper
interface UserRusGolfMapper {
    fun map(user: UserRusGolf): UserRusGolfDTO
}