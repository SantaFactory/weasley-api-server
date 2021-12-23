package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.domain.Auth
import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.UserDTO
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

    constructor()

    constructor(jwtKey: String) {
        this.jwtKey = jwtKey
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

            val email = userInfo.username;

            val jwt = JwtUtils.makeByJwtToken(
                jwtKey!!,
                userInfo.id,
                email,
                userInfo.name,
                createByAuthDomain(userInfo.authorities)
            )

            val message = AppMessageDTO(HttpStatus.OK.value(), UserDTO.Info(email, userInfo.userKey, jwt))

            response.writer.write(JsonUtils.convertObjectToJson(message))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun createByAuthDomain(authorities: Collection<GrantedAuthority>): MutableSet<Auth> {
        return authorities.map { role -> Auth(role.authority as String) }.toList().toMutableSet()
    }

}
