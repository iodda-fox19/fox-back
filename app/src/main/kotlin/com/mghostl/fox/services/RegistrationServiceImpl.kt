package com.mghostl.fox.services

import com.mghostl.fox.dto.NewUserData
import com.mghostl.fox.exceptions.UserWasAlreadyRegisteredException
import com.mghostl.fox.mappers.UserMapper
import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.model.RecentlyRegisteredUserDetails
import com.mghostl.fox.repository.UserRepository
import com.mghostl.fox.utils.JwtTokenUtil
import org.springframework.stereotype.Service

@Service
class RegistrationServiceImpl(
    private val smsCodeService: SmsCodeService,
    private val userRepository: UserRepository,
    private val userService: UserService,
    private val jwtTokenUtil: JwtTokenUtil,
    private val userMapper: UserMapper
): RegistrationService {

    override fun sendSms(phone: String) = userRepository.findByPhone(phone)?.let {
        throw UserWasAlreadyRegisteredException("User with phone $phone has been already registered")
    } ?: smsCodeService.sendNewCode(phone, "Registration code:")

    override fun checkCode(smsId: Int, code: String) = smsCodeService.checkCode(smsId, code)
        .let { it to jwtTokenUtil.generateToken(RecentlyRegisteredUserDetails(it.phone!!)) }

    override fun createUser(phone: String, userData: NewUserData) = userService.createUser(phone, userData)
        .let { userMapper.map(it) to jwtTokenUtil.generateToken(FoxUserDetails(it)) }
}