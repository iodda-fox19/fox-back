package com.mghostl.fox.services

import com.mghostl.fox.dto.GetGamesResponse
import com.mghostl.fox.mappers.GameMapper
import com.mghostl.fox.model.GameFilter
import com.mghostl.fox.repository.GameRepository
import com.mghostl.fox.repository.specifications.EmptyGameSpecification
import com.mghostl.fox.repository.specifications.GameSpecification
import com.mghostl.fox.repository.specifications.SearchCriteria
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class GameServiceImpl(
    private val gameRepository: GameRepository,
    private val gameMapper: GameMapper
): GameService {
    @Transactional
    override fun getGames(phone: String, limit: Int, offset: Int) =
        gameRepository.findByUserPhone(phone, PageRequest.of(offset, limit))
            .let { it.totalElements to it.map { game -> gameMapper.map(game) } }
            .let { GetGamesResponse(it.second.toSet(), it.first) }

    @Transactional
    override fun getGames(filter: GameFilter, limit: Int, offset: Int): GetGamesResponse {
        val gameSpecification = createGameSpecification(filter.gamersCount, "gamersCount", "=")
            .and(createGameSpecification(filter.clubId, "clubId", "="))
            .and(createGameSpecification(filter.countryId, "countryId", "="))
            .and(createGameSpecification(filter.handicapMax, "handicapMax", "<"))
            .and(createGameSpecification(filter.handicapMin, "handicapMin", ">"))
            .and(createGameSpecification(filter.numOfHoles, "holes", "="))
            .and(createGameSpecification(filter.onlyForMembers, "onlyMembers", "="))
        return gameRepository.findAll(gameSpecification, PageRequest.of(offset, limit))
            .let { it.totalElements to it.map { game -> gameMapper.map(game) } }
            .let { GetGamesResponse(it.second.toSet(), it.first) }
    }

    private fun createGameSpecification(value: Any?, key: String, operation: String) = value
        ?.let { GameSpecification(SearchCriteria(key, operation, value)) }
        ?: EmptyGameSpecification()
}