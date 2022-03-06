package com.mghostl.fox.schedulers

import com.mghost.fox.schedulers.RusGolfScheduler
import com.mghostl.fox.AbstractTest
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.rusgolf.model.UserDTO
import com.mghostl.fox.rusgolf.services.RusGolfService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import java.time.LocalDate

class RusGolfSchedulerTest: AbstractTest() {

    @Autowired
    lateinit var rusGolfScheduler: RusGolfScheduler

    @MockkBean
    lateinit var rusGolfService: RusGolfService

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @Sql(scripts = ["classpath:init.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    fun `should save in db`() {
        val rusGolfUsers = getRusGolfUsers()
        every { rusGolfService.getUsersData() } returns rusGolfUsers

        rusGolfScheduler.getRusGolfData()

        userRepository.findAll()
            .filter { rusGolfUsers.map { user -> user.golfRegistryIdRU }.contains(it.golfRegistryIdRU) }
            .forEach {
                val rusGolfUser = rusGolfUsers.first { user -> user.golfRegistryIdRU == it.golfRegistryIdRU}
                assertEquals(rusGolfUser.golfRegistryIdRU, it.golfRegistryIdRU)
                assertEquals(rusGolfUser.handicap, it.handicap)
                assertEquals(rusGolfUser.handicapUpdateAt, it.handicapUpdateAt!!.toLocalDate())
            }
    }
}

private fun getRusGolfUsers() = setOf(
    UserDTO("RU111111", "Авдеева Наталия Витальевна", LocalDate.of(2021, 4, 9), 52.2f),
    UserDTO("RU000871", "Абахов Олег Евгеньевич", LocalDate.of(2021, 4, 9), 15.2f),
    UserDTO("RU001295", "Абахова Екатерина Олеговна", LocalDate.of(2021, 10, 31), 23.0f),
    UserDTO("RU001293", "Абахова Олеся Олеговна", LocalDate.of(2021, 10, 31), 40.8f),
    UserDTO("RU000155", "Абахова Оксана Алексеевна", LocalDate.of(2020, 3, 1), 21.3f),
    UserDTO("RU006673", "Абашин Валентин Николаевич", LocalDate.of(2021, 8, 22), 21.3f),
    UserDTO("RU005666", "Акберов Рустам Олегович", LocalDate.of(2021, 8, 8), 54.0f),

    )