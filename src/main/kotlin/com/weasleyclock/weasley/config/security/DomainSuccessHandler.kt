package com.weasleyclock.weasley.config.security

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.utils.JsonUtils
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class DomainSuccessHandler : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {

        try {
            response!!.status = HttpStatus.OK.value()

            response.contentType = AppProperties.CONTENT_TYPE

            val message = AppMessageDTO(HttpStatus.OK.value(), "success login")

            response.writer.write(JsonUtils.convertObjectToJson(message))

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
