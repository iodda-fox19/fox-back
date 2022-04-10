package com.mghostl.fox.dto

class GetGamesResponse(data: Set<GameDTO>, count: Long) : GetPageableResponse<GameDTO>(data, count)