package com.mghost.fox.schedulers

import com.mghostl.fox.rusgolf.services.RusGolfService
import mu.KLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class RusGolfScheduler(
    private val rusGolfService: RusGolfService
) {

    companion object: KLogging()

    @Scheduled(cron = "0 0 2 * * *")
    fun getRusGolfData() {
        logger.info("collecting data from rusgolf")
        rusGolfService.getUsersData()
    }
}