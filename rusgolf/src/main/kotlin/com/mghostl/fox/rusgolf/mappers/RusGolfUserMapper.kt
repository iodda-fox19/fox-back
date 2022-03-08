package com.mghostl.fox.rusgolf.mappers

import com.mghostl.fox.model.UserRusGolf
import com.mghostl.fox.rusgolf.model.UserDTO
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings

@Mapper
abstract class RusGolfUserMapper {
    @Mappings(
        Mapping(target = "firstName", ignore = true),
        Mapping(target = "lastName", ignore = true),
        Mapping(target = "middleName", ignore = true),
        Mapping(target = "updateDateTime", ignore = true),
    )
    abstract fun map(userDTO: UserDTO): UserRusGolf

    @AfterMapping
    protected fun map (@MappingTarget user: UserRusGolf, userDTO: UserDTO) {
        user.apply {
            lastName = userDTO.fio.split(" ")[0]
            firstName = userDTO.fio.split(" ")[1]
            middleName = userDTO.fio.split(" ")[2]
        }
    }
}