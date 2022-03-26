package com.mghostl.fox.mappers

import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper
interface UserMapper {
    @Mappings(
        Mapping(target = "homeClub", source = "homeClub.name")
    )
    abstract fun map(user: User): UserDto
}