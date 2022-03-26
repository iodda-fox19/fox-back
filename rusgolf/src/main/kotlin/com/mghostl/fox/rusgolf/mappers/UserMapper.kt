package com.mghostl.fox.rusgolf.mappers

import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import java.time.ZoneId

@Mapper(implementationName = "UserMapperRusGolf")
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
        Mapping(target = "lastName", ignore = true),
        Mapping(target = "homeClub", ignore = true),
        Mapping(target = "avatar", ignore = true),
        Mapping(target = "submittedTrainer", ignore = true),
        Mapping(target = "submittedAdministrator", ignore = true),
        Mapping(target = "submittedHandicap", ignore = true),
        Mapping(target = "toAddEventsInCalendar", ignore = true),
        Mapping(target = "blocked", ignore = true),
        Mapping(target = "deleted", ignore = true),
    )
    abstract fun map(userDTO: RusGolfUserDTO): User

    @AfterMapping
    protected fun map(@MappingTarget user: User, userDTO: RusGolfUserDTO) {
        user.apply {
            handicapUpdateAt = userDTO.handicapUpdateAt.atStartOfDay(ZoneId.systemDefault())
            name = userDTO.getName()
            lastName = userDTO.getLastName()
        }
    }

    private fun RusGolfUserDTO.getName() = fio.split(" ")[1]

    private fun RusGolfUserDTO.getLastName() = fio.split(" ")[0]
}