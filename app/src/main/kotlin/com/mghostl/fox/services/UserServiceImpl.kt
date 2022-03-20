package com.mghostl.fox.services

import com.mghostl.fox.model.User
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.rusgolf.exceptions.UserNotFoundException
import com.mghostl.fox.rusgolf.model.UserDTO
import mu.KLogging
import org.springframework.stereotype.Service
import java.time.ZoneId
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
): UserService {

    companion object: KLogging()

    @Transactional
    override fun updateUser(user: User, userDTO: UserDTO): User {
        logger.info { "updating db with user $user" }
        return user.apply {
                handicap = userDTO.handicap
                handicapUpdateAt = userDTO.handicapUpdateAt.atStartOfDay(ZoneId.systemDefault())
            }.let {save(it) }
    }

    @Transactional
    override fun findByGolfRegistryId(golfRegistryId: String) =
        userRepository.findByGolfRegistryIdRU(golfRegistryId)

    @Transactional
    override fun findByPhone(phone: String) = userRepository.findByPhone(phone)

    @Transactional
    override fun findById(id: Int): User = userRepository.findById(id)
        .orElseThrow { UserNotFoundException("There is no user with id $id") }

    private fun save(user: User) = userRepository.save(user)
}