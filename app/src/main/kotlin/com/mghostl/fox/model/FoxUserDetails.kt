package com.mghostl.fox.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class FoxUserDetails(
     private val user: User
): UserDetails, Serializable {

    private val authorities: MutableList<SimpleGrantedAuthority>

    init {
        val roles = mutableListOf("ROLE_USER")
        with(user) {
            if(isAdmin == true) roles.add("ROLE_ADMIN")
            if(isGamer == true) roles.add("ROLE_GAMER")
            if(isReferee == true) roles.add("ROLE_REFEREE")
            if(isTrainer == true) roles.add("ROLE_TRAINER")
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