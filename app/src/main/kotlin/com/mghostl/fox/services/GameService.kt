package com.mghostl.fox.services

import com.mghostl.fox.dto.GetGamesResponse
import com.mghostl.fox.model.GameFilter

interface GameService {
    fun getGames(phone: String, limit: Int, offset: Int): GetGamesResponse
    fun getGames(filter: GameFilter, limit: Int, offset: Int): GetGamesResponse
}