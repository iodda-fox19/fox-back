package com.mghostl.fox.repository

import com.mghostl.fox.model.Game
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface GameRepository: JpaRepository<Game, Int>, JpaSpecificationExecutor<Game> {
    @Query("select g " +
        "from Game g " +
        "INNER JOIN g.users u " +
        "WHERE u.phone = :phone")
    fun findByUserPhone(phone: String, pageable: Pageable): Page<Game>
}