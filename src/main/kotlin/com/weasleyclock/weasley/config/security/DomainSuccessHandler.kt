package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.UserDTO
import com.weasleyclock.weasley.service.TokenService
import com.weasleyclock.weasley.utils.JsonUtils
import com.weasleyclock.weasley.utils.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DomainSuccessHandler : AuthenticationSuccessHandler {

    private var jwtKey: String? = null

    private var tokenService: TokenService? = null

    constructor()

    constructor(jwtKey: String, tokenService: TokenService) {
        this.jwtKey = jwtKey
        this.tokenService = tokenService
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?
    ) {

        try {

            val userInfo = authentication!!.principal as DomainUserDetail

            response!!.status = HttpStatus.OK.value()

            response.contentType = AppProperties.CONTENT_TYPE

            val email = userInfo.username

            val userId = userInfo.user!!.id

            val userName = userInfo.user!!.name

            val authorities = createByArrayToString(userInfo.authorities)

            val accessToken = JwtUtils.createByAccessToken(
                jwtKey!!, userId, email, userName, authorities
            )

            val refreshToken = JwtUtils.createByRefreshToken(
                jwtKey!!, userId, email, userName, authorities
            )

            val message = AppMessageDTO(
                HttpStatus.OK.value(), UserDTO.Info(email, userInfo.user!!.userKey, accessToken, refreshToken)
            )

            tokenService!!.createByToken(userId, refreshToken)

            response.writer.write(JsonUtils.convertObjectToJson(message))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createByArrayToString(authorities: Collection<GrantedAuthority>): List<String> =
        authorities.map { role -> role.authority as String }.toList()

}
