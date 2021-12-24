package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.config.security.CachingFilter
import com.weasleyclock.weasley.config.security.JwtValidationFilter
import com.weasleyclock.weasley.config.security.OpenIdConnectFilter
import com.weasleyclock.weasley.repository.UserRepository
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
class SecurityConfiguration(private val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {

    @Value("\${google.jwkUrl}")
    private var jwkUrl: String? = null

    @Value("\${jwt.key}")
    private var jwtKey: String? = null

    override fun configure(http: HttpSecurity?) {

        http!!
            // todo : 나중에 풀기
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
    }

    override fun configure(web: WebSecurity?) {
    }

    @Bean
    fun openIdConnectFilter(): OpenIdConnectFilter {
        return OpenIdConnectFilter("/login-process", jwkUrl.toString(), userRepository, jwtKey.toString())
    }

}
