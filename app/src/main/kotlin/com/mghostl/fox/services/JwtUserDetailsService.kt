package com.mghostl.fox.services

import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.model.RecentlyRegisteredUserDetails
import com.mghostl.fox.repository.SmsRepository
import com.mghostl.fox.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository,
    private val smsRepository: SmsRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String) = userRepository.findByPhone(username)?.let { FoxUserDetails(it) }
            ?: smsRepository.findAllByPhone(username).firstOrNull()?.let { RecentlyRegisteredUserDetails(username) }
            ?:throw UsernameNotFoundException("User not found with username: $username")

}