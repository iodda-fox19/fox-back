package com.mghostl.fox.utils

import com.mghostl.fox.model.City
import com.mghostl.fox.model.Club
import com.mghostl.fox.model.Country
import com.mghostl.fox.model.Sex
import com.mghostl.fox.model.User
import com.mghostl.fox.rusgolf.model.RusGolfUserDTO
import java.time.LocalDate

fun rusGolfUserDTO() = RusGolfUserDTO("RU004947", "Авдеева Наталия Витальевна", LocalDate.of(2021,9, 8), 52.2f, Sex.FEMALE)

fun rusGolfUsers() = setOf(rusGolfUserDTO())

fun user() = User(name = "Admin", phone = "+7 (900) 000-00-00", email = "admin@admin.com",
isAdmin = true, about = "Самый главный дядя тут", golfRegistryIdRU = "RU004947", lastName = "Adminov", handicap = 52.2f, homeClub = club())

fun club() = Club(name = "The best club", city = City(name = "Tbilisi", geonameId = 1234), country = Country(name = "Georgia", geonameId = 123))