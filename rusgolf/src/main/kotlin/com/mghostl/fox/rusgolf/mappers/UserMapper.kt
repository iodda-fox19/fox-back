package com.mghostl.fox.rusgolf.mappers

import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.UserDTO
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import java.time.ZoneId

@Mapper
abstract class UserMapper {
    @Mappings(
        Mapping(target = "handicapUpdateAt", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "name", ignore = true),
        Mapping(target = "phone", ignore = true),
        Mapping(target = "email", ignore = true),
        Mapping(target = "password", ignore = true),
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true),
        Mapping(target = "admin", ignore = true),
        Mapping(target = "referee", ignore = true),
        Mapping(target = "gamer", ignore = true),
        Mapping(target = "trainer", ignore = true),
        Mapping(target = "about", ignore = true),
        Mapping(target = "lastName", ignore = true)
    )
    abstract fun map(userDTO: UserDTO): User

    @AfterMapping
    protected fun map(@MappingTarget user: User, userDTO: UserDTO) {
        user.apply {
            handicapUpdateAt = userDTO.handicapUpdateAt.atStartOfDay(ZoneId.systemDefault())
            name = userDTO.getName()
            lastName = userDTO.getLastName()
        }
    }

    private fun UserDTO.getName() = fio.split(" ")[1]

    private fun UserDTO.getLastName() = fio.split(" ")[0]
}