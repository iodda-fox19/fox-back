package com.mghostl.fox.mappers

import com.mghostl.fox.dto.ForeignUserDto
import com.mghostl.fox.dto.NewUserData
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.model.User
import com.mghostl.fox.repository.ClubRepository
import org.mapstruct.AfterMapping
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.Mappings
import org.springframework.beans.factory.annotation.Autowired

@Mapper(uses = [ClubRepository::class])
abstract class UserMapper {

    @Autowired
    lateinit var clubRepository: ClubRepository

    @Mappings(
        Mapping(target = "homeClub", source = "homeClub.name")
    )
    abstract fun map(user: User): UserDto

    @Mappings(
        Mapping(target = "homeClub", source = "homeClub.name")
    )
    abstract fun mapToForeignUser(user: User): ForeignUserDto

    @Mappings(
        Mapping(target = "homeClub", ignore = true),
        Mapping(target = "email", ignore = true),
        Mapping(target = "password", ignore = true),
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true),
        Mapping(target = "referee", ignore = true),
        Mapping(target = "about", ignore = true),
        Mapping(target = "handicapUpdateAt", ignore = true),
    )
    abstract fun map(userDto: UserDto): User

    @AfterMapping
    fun map(userDto: UserDto, @MappingTarget user: User) {
        user.homeClub = userDto.homeClub?.let { clubRepository.findByName(it) }
    }

    @Mappings(
        Mapping(target = "homeClub", ignore = true),
        Mapping(target = "email", ignore = true),
        Mapping(target = "password", ignore = true),
        Mapping(target = "createdAt", ignore = true),
        Mapping(target = "updatedAt", ignore = true),
        Mapping(target = "referee", ignore = true),
        Mapping(target = "about", ignore = true),
        Mapping(target = "handicapUpdateAt", ignore = true),
        Mapping(target = "id", ignore = true),
        Mapping(target = "admin", ignore = true),
        Mapping(target = "gamer", ignore = true),
        Mapping(target = "trainer", ignore = true),
        Mapping(target = "golfRegistryIdRU", ignore = true),
        Mapping(target = "avatar", ignore = true),
        Mapping(target = "lastName", ignore = true),
        Mapping(target = "submittedTrainer", ignore = true),
        Mapping(target = "submittedAdministrator", ignore = true),
        Mapping(target = "submittedHandicap", ignore = true),
        Mapping(target = "toAddEventsInCalendar", ignore = true),
        Mapping(target = "blocked", ignore = true),
        Mapping(target = "deleted", ignore = true),
        Mapping(target = "handicap", ignore = true),

    )
    abstract fun map(userData: NewUserData, phone: String): User
}