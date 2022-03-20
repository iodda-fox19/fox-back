package com.mghostl.fox.services

import com.mghostl.fox.model.FoxUserDetails
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class JwtUserDetailsService(
    private val userService: UserService
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userService.findByPhone(username) ?:throw UsernameNotFoundException("User not found with username: $username")
        return FoxUserDetails(user)
    }
}