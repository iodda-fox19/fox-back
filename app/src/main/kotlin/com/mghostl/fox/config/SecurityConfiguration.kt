package com.mghostl.fox.config

import com.mghostl.fox.filters.JwtTokenFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Profile("!no-auth")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfiguration(
    private val jwtTokenFilter: JwtTokenFilter,
) : WebSecurityConfigurerAdapter() {

    @Value("\${spring.security.user.name}")
    lateinit var username: String

    @Value("\${spring.security.user.password}")
    lateinit var password: String

    override fun configure(http: HttpSecurity) {
        http
            .addFilterBefore(jwtTokenFilter, BasicAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers(HttpMethod.POST,  "/api/sms").permitAll()
            .antMatchers(HttpMethod.PUT,  "/api/sms/**").permitAll()
            .antMatchers("/h2-console/**", "/actuator/**").hasRole("ADMIN")
            .antMatchers("/api/**").hasRole("GAMER")
            .and().httpBasic()
            .and().csrf().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
        auth
            .inMemoryAuthentication()
            .withUser(username)
            .password(encoder.encode(password))
            .roles("ADMIN")
    }

    @Bean
    fun corsFilter() = UrlBasedCorsConfigurationSource().apply {
        registerCorsConfiguration("/**", CorsConfiguration().apply {
            allowCredentials = true
            allowedOrigins = listOf("*")
            allowedHeaders = listOf("*")
            allowedMethods = listOf("*")
        })
    }.let { CorsFilter(it) }
}