package com.mghostl.fox.services

import com.mghostl.fox.checkers.UpdateChecker
import com.mghostl.fox.dto.UserDto
import com.mghostl.fox.mappers.UserMapper
import com.mghostl.fox.model.PatchUserRequest
import com.mghostl.fox.model.User
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.rusgolf.exceptions.UserNotFoundException
import mu.KLogging
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val updateCheckers: List<UpdateChecker<User>>
): UserService {

    companion object: KLogging()


    @Transactional
    override fun updateUser(userDto: UserDto, phone: String): UserDto {
        val user = findUserByPhone(phone)
        val updatingUser = userMapper.map(userDto).fillWithIgnoringFields(user)
            .apply { id = user.id }

        if(updatingUser.handicap != user.handicap && user.isSubmittedHandicap) updatingUser.isSubmittedHandicap = false

        updateCheckers.forEach { it.check(user, updatingUser) }

        ignoreDeletingFields(updatingUser, user)

        logger.info { "Saving user with id ${updatingUser.id}" }
        logger.info { "updating user is $updatingUser" }
        return save(updatingUser)
            .also { logger.info { "User was saved $it" } }
            .let { userMapper.map(it) }
    }

    private fun ignoreDeletingFields(updatingUser: User, user: User) {
        if (updatingUser.name == null) {
            updatingUser.name = user.name
        }
        if (updatingUser.handicap == null) {
            updatingUser.handicap = user.handicap
        }
        if (updatingUser.toAddEventsInCalendar == null) {
            updatingUser.toAddEventsInCalendar = user.toAddEventsInCalendar
        }
    }

    @Transactional
    override fun findByGolfRegistryId(golfRegistryId: String) =
        userRepository.findByGolfRegistryIdRU(golfRegistryId)

    @Transactional
    override fun findByPhone(phone: String) = findUserByPhone(phone).let { userMapper.map(it) }

    private fun findUserByPhone(phone: String) = userRepository.findByPhone(phone) ?: throw EntityNotFoundException("There is no user with this phone: $phone")

    @Transactional
    override fun findById(id: Int): User = userRepository.findById(id)
        .orElseThrow { UserNotFoundException("There is no user with id $id") }

    @Transactional
    override fun deleteUser(phone: String) {
        findUserByPhone(phone)
            .apply { isDeleted = true }
            .also { save(it) }
    }

    override fun patchUser(userId: Int, patchUserRequest: PatchUserRequest) = findById(userId)
        .apply {
            patchUserRequest.isBlocked?.let { isBlocked = it }
            patchUserRequest.isDeleted?.let { isDeleted = it }
            patchUserRequest.isSubmittedAdmin?.let { isSubmittedAdministrator = it }
            patchUserRequest.isSubmittedTrainer?.let { isSubmittedTrainer = it }
        }
        .also { save(it) }
        .let { userMapper.map(it) }

    private fun save(user: User) = userRepository.save(user)

    private fun User.fillWithIgnoringFields(user: User) = apply {
        isSubmittedAdministrator = user.isSubmittedAdministrator
        isSubmittedTrainer = user.isSubmittedTrainer
        isSubmittedHandicap = user.isSubmittedHandicap
        isBlocked = user.isBlocked
        isDeleted = user.isDeleted
    }
}