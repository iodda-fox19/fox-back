package com.mghostl.fox.repository

import com.mghostl.fox.model.Club
import org.springframework.data.jpa.repository.JpaRepository

interface ClubRepository: JpaRepository<Club, Int> {
    fun findByName(name: String): Club?
}