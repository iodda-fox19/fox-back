package com.mghostl.fox.rusgolf.schedulers

import com.mghostl.fox.rusgolf.mappers.RusGolfUserMapper
import com.mghostl.fox.rusgolf.services.RusGolfService
import com.mghostl.fox.rusgolf.services.RusGolfUserService
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RusGolfScheduler(
    private val rusGolfService: RusGolfService,
    private val rusGolfUserService: RusGolfUserService,
    private val rusgolfUserMapper: RusGolfUserMapper
) {

    companion object: KLogging()

    @Scheduled(cron = "0 1 0 * * *")
    fun getRusGolfData() {
        logger.info("collecting data from rusgolf")
        rusGolfService.getUsersData()
            .map { rusgolfUserMapper.map(it) }
            .onEach { rusGolfUserService.save(it) }
            .forEach{ logger.info { "Completing saving data for rusGolfId: ${it.golfRegistryIdRU}" }}
    }
}