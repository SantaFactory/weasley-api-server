package com.weasleyclock.weasley.config.security

import com.auth0.jwk.SigningKeyNotFoundException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorType
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CachingFilter : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val contentCachingRequestWrapper = ContentCachingRequestWrapper(request)

            val contentCachingResponseWrapper = ContentCachingResponseWrapper(response)

            filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper)

            contentCachingResponseWrapper.copyBodyToResponse()
        } catch (e: SigningKeyNotFoundException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorType.A001)
        } catch (e: Exception) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorType.S001)
        }

    }

    private fun setErrorResponse(
        status: HttpStatus,
        response: HttpServletResponse,
        ex: Exception,
        erroType: ErrorType
    ) {

        response.status = status.value()

        response.contentType = "application/json"

        val detailMessage = ExceptionUtils.getStackTrace(ex)

        val errorDTO = ErrorDTO(erroType.code, erroType.message, detailMessage)

        try {
            response.writer.write(convertObjectToJson(errorDTO));
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(JsonProcessingException::class)
    private fun convertObjectToJson(any: Any?): String {
        if (any == null) {
            return ""
        }
        val mapper = ObjectMapper()
        return mapper.writeValueAsString(any)
    }

}

