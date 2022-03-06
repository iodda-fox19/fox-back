package com.mghostl.fox.rusgolf.services

import com.mghostl.fox.rusgolf.clients.RusGolfClient
import com.mghostl.fox.rusgolf.model.UserDTO
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class RusGolfServiceImpl(
    private val rusGolfClient: RusGolfClient
): RusGolfService {

    companion object: KLogging()

    override fun getUsersData(): Set<UserDTO> {
        logger.info { "Getting user data from rusGolf" }
        return rusGolfClient.getUserData()
            .also { logger.info { "Successfully got user data from rusgolf" } }
            .also { logger.debug { "data is $it" } }
    }
}