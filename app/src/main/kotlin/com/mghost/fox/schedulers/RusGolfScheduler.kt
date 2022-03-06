package com.mghost.fox.schedulers

import com.mghost.fox.services.UserService
import com.mghostl.fox.rusgolf.services.RusGolfService
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RusGolfScheduler(
    private val rusGolfService: RusGolfService,
    private val userService: UserService
) {

    companion object: KLogging()

    @Scheduled(cron = "0 0 2 * * *")
    fun getRusGolfData() {
        logger.info("collecting data from rusgolf")
        rusGolfService.getUsersData()
            .map { userService.findByGolfRegistryId(it.golfRegistryIdRU) to it }
            .filter { it.first != null }
            .onEach { userService.updateUser(it.first!!, it.second) }
            .forEach{ logger.info { "Completing saving data for rusGolfId: ${it.first!!.golfRegistryIdRU}" }}
    }
}