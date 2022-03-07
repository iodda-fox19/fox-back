package com.mghostl.fox.rusgolf.mappers

import com.mghostl.fox.model.UserRusGolf
import com.mghostl.fox.rusgolf.model.UserDTO
import org.mapstruct.Mapper

@Mapper
interface RusGolfUserMapper {
    fun map(userDTO: UserDTO): UserRusGolf
}