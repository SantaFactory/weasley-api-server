package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.config.security.CachingFilter
import com.weasleyclock.weasley.config.security.JwtValidationExceptionFilter
import com.weasleyclock.weasley.config.security.JwtValidationFilter
import com.weasleyclock.weasley.config.security.OpenIdConnectFilter
import com.weasleyclock.weasley.service.TokenService
import com.weasleyclock.weasley.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    private val userService: UserService,
    private val tokenService: TokenService
) : WebSecurityConfigurerAdapter() {

    @Value("\${google.jwkUrl}")
    private var jwkUrl: String? = null

    @Value("\${jwt.key}")
    private var jwtKey: String? = null

    override fun configure(http: HttpSecurity?) {
        http!!
            .cors()
            .disable()
            .csrf()
            .disable()
            // login filter
            .addFilterBefore(
                CachingFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                openIdConnectFilter(),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                JwtValidationFilter(jwtKey.toString()),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(
                JwtValidationExceptionFilter(),
                JwtValidationFilter::class.java
            )
    }

    override fun configure(web: WebSecurity?) {
    }

    @Bean
    fun openIdConnectFilter(): OpenIdConnectFilter {
        return OpenIdConnectFilter(
            "/login-process",
            jwkUrl.toString(),
            userService,
            tokenService,
            jwtKey.toString()
        )
    }

}
