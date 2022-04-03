package com.mghostl.fox.config

import com.mghostl.fox.model.FoxUserDetails
import com.mghostl.fox.model.RecentlyRegisteredUserDetails
import com.mghostl.fox.model.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockFoxUserSecurityContextFactory: WithSecurityContextFactory<WithMockFoxUser> {
    override fun createSecurityContext(annotation: WithMockFoxUser): SecurityContext {
       val userDetails =  if(annotation.roles.contains("NEW_USER")) {
            RecentlyRegisteredUserDetails(annotation.phone)
        }
        else {
           FoxUserDetails(
               User(
                   phone = annotation.phone,
                   isAdmin = annotation.roles.contains("ADMIN"),
                   isGamer = annotation.roles.contains("GAMER"),
                   isReferee = annotation.roles.contains("REFEREE"),
                   isTrainer = annotation.roles.contains("TRAINER")
               )
           )
       }
        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        return SecurityContextHolder.createEmptyContext()
            .apply { this.authentication = authentication }
    }
}