package com.mghostl.fox.mappers

import com.mghostl.fox.dto.GameDTO
import com.mghostl.fox.model.Game
import org.mapstruct.Mapper

@Mapper(uses = [UserMapper::class])
interface GameMapper {
    fun map(game: Game): GameDTO
}