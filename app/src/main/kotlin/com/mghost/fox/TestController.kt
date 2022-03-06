package com.mghost.fox

import com.mghost.fox.schedulers.RusGolfScheduler
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Profile("local")
@RestController
@RequestMapping("test")
class TestController(
    private val rusGolfScheduler: RusGolfScheduler
) {
    @PatchMapping
    fun updateUsersWithRusGolf() {
        rusGolfScheduler.getRusGolfData()
    }
}