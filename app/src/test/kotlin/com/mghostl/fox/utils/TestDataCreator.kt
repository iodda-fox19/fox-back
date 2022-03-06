package com.mghostl.fox.utils

import com.mghostl.fox.rusgolf.model.UserDTO
import java.time.LocalDate

fun userDTO() = UserDTO("RU004947", "Авдеева Наталия Витальевна", LocalDate.of(2021,9, 8), 52.2f)

fun users() = setOf(userDTO())