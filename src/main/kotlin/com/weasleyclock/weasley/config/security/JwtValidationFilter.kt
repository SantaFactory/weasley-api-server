package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.domain.Auth
import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.utils.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtValidationFilter(jwtKey: String) : OncePerRequestFilter() {

    private var jwtKey: String? = jwtKey

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)

        val claims = JwtUtils.parseJwtToken(authorization, jwtKey!!, true)

        val id = (claims["id"] as Int).toLong()

        val email = claims["email"] as String
        val name = claims["name"] as String
        val authorities = (claims["authorities"] as List<String>).map { title -> Auth(title) }.toMutableSet()

        val jwtLoginUser = User(id, email, name, authorities)

        val jwtLoginDetail = DomainUserDetail(jwtLoginUser)

        val userToken = UsernamePasswordAuthenticationToken(jwtLoginDetail, null, jwtLoginDetail.authorities)

        userToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = userToken

        filterChain.doFilter(request, response)

    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api")
    }

}
