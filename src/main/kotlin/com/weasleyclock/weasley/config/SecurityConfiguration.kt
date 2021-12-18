package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.config.security.CachingFilter
import com.weasleyclock.weasley.config.security.OpenIdConnectFilter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfiguration() : WebSecurityConfigurerAdapter() {

    @Value("\${google.jwkUrl}")
    private var jwkUrl: String? = null

    override fun configure(http: HttpSecurity?) {

        http!!.authorizeRequests()
            .antMatchers("/swagger-ui/**")
            .permitAll()
            .and()
            // todo : 나중에 풀기
            .cors()
            .disable()
            .csrf()
            .disable()
            // login filter
            .addFilterBefore(CachingFilter() , UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(OpenIdConnectFilter("/login-process" , jwkUrl.toString()), UsernamePasswordAuthenticationFilter::class.java)

    }

    override fun configure(web: WebSecurity?) {
        web!!.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
    }

}
