package com.weasleyclock.weasleyclient.config.security

import com.weasleyclock.weasleyclient.domain.User
import com.weasleyclock.weasleyclient.enmus.RoleType
import com.weasleyclock.weasleyclient.utils.JwtUtils
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
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

        val authority = RoleType.valueOf(claims["authority"] as String)

        val jwtLoginUser = User(id, email, name, authority)

        val jwtLoginDetail = DomainUserDetail(jwtLoginUser, listOf(SimpleGrantedAuthority(authority.name)))

        val userToken = UsernamePasswordAuthenticationToken(jwtLoginDetail, null, jwtLoginDetail.authorities)

        userToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = userToken

        filterChain.doFilter(request, response)

    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.servletPath.startsWith("/api")
    }

}
