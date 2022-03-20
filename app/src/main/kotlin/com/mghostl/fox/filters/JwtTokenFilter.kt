package com.mghostl.fox.filters

import com.mghostl.fox.services.JwtUserDetailsService
import com.mghostl.fox.utils.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import mu.KLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    private val jwtTokenUtil: JwtTokenUtil,
    private val jwtUserDetailsService: JwtUserDetailsService
): OncePerRequestFilter() {

    companion object: KLogging()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header = request.getHeader("Authorization")

        if (header.isNullOrEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        // Get jwt token and validate
        val token = header.split(" ")[1].trim()

        if (!jwtTokenUtil.validateToken(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val username = try {
            jwtTokenUtil.getUsernameFromToken(token)
        } catch (e: IllegalArgumentException) {
            logger.info {"Unable to get jwt token"}
            null
        } catch (e: ExpiredJwtException) {
            logger.info { "Jwt token was expired"}
            null
        }
        if (username == null ||  SecurityContextHolder.getContext().authentication != null ) {
           filterChain.doFilter(request, response)
            return
        }


        val userDetails = jwtUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(token))
        if(userDetails == null) {
            filterChain.doFilter(request, response)
            return
        }


        val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

}