package com.mghostl.fox.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class FoxUserDetails(
     private val user: User
): UserDetails {

    private val authorities: MutableList<SimpleGrantedAuthority>

    init {
        val roles = mutableListOf("ROLE_USER")
        with(user) {
            if(isAdmin) roles.add("ROLE_ADMIN")
            if(isGamer) roles.add("ROLE_GAMER")
            if(isReferee) roles.add("ROLE_REFEREE")
            if(isTrainer) roles.add("ROLE_TRAINER")
        }
        authorities =  roles.map { SimpleGrantedAuthority(it) }.toMutableList()
    }
    override fun getAuthorities() = authorities

    override fun getPassword() = user.password

    override fun getUsername() = user.phone

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}