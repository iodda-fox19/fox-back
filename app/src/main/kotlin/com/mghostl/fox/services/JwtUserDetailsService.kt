package com.mghostl.fox.services

import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userRepository: UserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findByPhone(username) ?:throw UsernameNotFoundException("User not found with username: $username")
        return FoxUserDetails(user)
    }
}