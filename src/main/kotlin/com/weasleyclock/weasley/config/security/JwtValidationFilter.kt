package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.domain.Auth
import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.utils.JwtUtils
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.beans.factory.support.ManagedSet
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.security.SignatureException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtValidationFilter : OncePerRequestFilter {

    private var jwtKey: String? = null

    constructor()

    constructor(jwtKey: String) {
        this.jwtKey = jwtKey
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        try {

            val authorization = request.getHeader(HttpHeaders.AUTHORIZATION)

            val claims = JwtUtils.parseJwtToken(authorization, jwtKey!!)

            val id = claims["id"] as Int

            val email = claims["email"] as String

            val name = claims["name"] as String

            val authorities = claims["authorities"] as List<Auth>

            val jwtLoginUser = User(id.toLong(), email, name, ManagedSet())

            val jwtLoginDetail = DomainUserDetail(jwtLoginUser)

            val userToken = UsernamePasswordAuthenticationToken(jwtLoginDetail, null, jwtLoginDetail.authorities)

            userToken.details = WebAuthenticationDetailsSource().buildDetails(request)

            SecurityContextHolder.getContext().authentication = userToken

            filterChain.doFilter(request, response)

        } catch (e: Exception) {
            // 모든 예외 처리
            e.printStackTrace();
        } catch (e: UnsupportedJwtException) {
            // 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
        } catch (e: MalformedJwtException) {
            // JWT가 올바르게 구서오디지 않았을 때
        } catch (e: ExpiredJwtException) {
            // JWT를 생성할 때 지정한 유효기간이 초과되었을 때
        } catch (e: SignatureException) {
            // JWT의 기존 서명을 확인하지 못했을 때
        } catch (e: IllegalArgumentException) {
            // 위의 예외에 대해 적절한 처리를 해주는 것이 좋습니다!
        }

    }

}
