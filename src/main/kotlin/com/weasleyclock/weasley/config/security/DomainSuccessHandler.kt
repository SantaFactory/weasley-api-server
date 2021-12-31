package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.domain.Token
import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.UserDTO
import com.weasleyclock.weasley.repository.TokenRepository
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

    private var tokenRepository: TokenRepository? = null

    constructor()

    constructor(jwtKey: String, tokenRepository: TokenRepository) {
        this.jwtKey = jwtKey
        this.tokenRepository = tokenRepository
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {

        try {

            val userInfo = authentication!!.principal as DomainUserDetail

            response!!.status = HttpStatus.OK.value()

            response.contentType = AppProperties.CONTENT_TYPE

            val email = userInfo.username

            val userId = userInfo.id

            val userName = userInfo.name

            val authorities = createByArrayToString(userInfo.authorities)

            val accessToken = JwtUtils.createByAccessToken(
                jwtKey!!,
                userId,
                email,
                userName,
                authorities
            )

            val refreshToken = JwtUtils.createByRefreshToken(
                jwtKey!!,
                userId,
                email,
                userName,
                authorities
            )

            val message =
                AppMessageDTO(HttpStatus.OK.value(), UserDTO.Info(email, userInfo.userKey, accessToken, refreshToken))

            createByToken(userId, refreshToken)

            response.writer.write(JsonUtils.convertObjectToJson(message))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createByToken(userId: Long?, refreshToken: String?) {

        val foundTokenOptional = tokenRepository!!.findByUserId(userId!!)

        if (foundTokenOptional.isPresent) {

            val foundToken = foundTokenOptional.get()

            foundToken.token = refreshToken

        } else {

            tokenRepository!!.save(Token(userId!!, refreshToken!!))

        }

    }

    private fun createByArrayToString(authorities: Collection<GrantedAuthority>): List<String> =
        authorities.map { role -> role.authority as String }.toList()

}
