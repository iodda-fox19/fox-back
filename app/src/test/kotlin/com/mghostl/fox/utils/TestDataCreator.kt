package com.mghostl.fox.utils

import com.mghostl.fox.model.Avatar
import com.mghostl.fox.model.City
import com.mghostl.fox.model.Club
import com.mghostl.fox.model.Country
import com.mghostl.fox.model.Game
import com.mghostl.fox.model.Sex
import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

fun rusGolfUserDTO() = RusGolfUserDTO("RU004947", "Авдеева Наталия Витальевна", LocalDate.of(2021,9, 8), 52.2f, Sex.FEMALE)

fun rusGolfUsers() = setOf(rusGolfUserDTO())

fun user() = User(name = "Admin", phone = "+7 (900) 000-00-00", email = "admin@admin.com", isGamer = true,
isAdmin = true, about = "Самый главный дядя тут", golfRegistryIdRU = "RU004947", lastName = "Adminov", handicap = 52.2f, homeClub = club(),
toAddEventsInCalendar = false, avatar = Avatar("fileName", "url"))

fun game() = Game(clubId = 1, countryId = 1, date = LocalDate.now(), holes = 3, gamersCount = 4, reserved = false,
memberPrice = BigDecimal.valueOf(1000), guestPrice = BigDecimal.valueOf
(2000), time = LocalTime.MIDNIGHT, description = "Big game", name = "Big big name", onlyMembers = false,
handicapMax = 53f, handicapMin = 1.5f)

fun club() = Club(name = "The best club")

private fun country() {
    Country(name = "Georgia", geonameId = 123)
}

private fun city() {
    City(name = "Tbilisi", geonameId = 1234)
}

fun gamer() = User(name = "Admin", phone = "+7 (900) 000-00-00", email = "admin@admin.com",
    isAdmin = false,  isGamer = true, about = "Самый главный дядя тут", golfRegistryIdRU = "RU004947", lastName = "Adminov", handicap = 52.2f, homeClub = club())