package com.mghostl.fox.rusgolf.services

import com.mghostl.fox.mappers.UserRusGolfMapper
import com.mghostl.fox.model.UserRusGolf
import com.mghostl.fox.repository.UserRusGolfRepository
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class RusGolfUserServiceImpl(
    private val userRusGolfRepository: UserRusGolfRepository,
    private val userRusGolfMapper: UserRusGolfMapper
): RusGolfUserService {

    @Transactional
    override fun save(userRusGolf: UserRusGolf) = userRusGolfRepository.save(userRusGolf)

    @Transactional
    override fun getUser(golfRegistryIdRU: String) =
        userRusGolfRepository.findById(golfRegistryIdRU)
            .orElseThrow { EntityNotFoundException("There is no rusgolf user with such id $golfRegistryIdRU") }
            .let { userRusGolfMapper.map(it) }
}