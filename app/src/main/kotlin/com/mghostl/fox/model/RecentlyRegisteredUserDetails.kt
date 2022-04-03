package com.mghostl.fox.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class RecentlyRegisteredUserDetails(private val phone: String): UserDetails, Serializable {
    override fun getAuthorities() = mutableListOf("ROLE_NEW_USER").map { SimpleGrantedAuthority(it) }

    override fun getPassword() = null

    override fun getUsername() = phone

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}