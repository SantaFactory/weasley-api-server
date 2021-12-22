package com.weasleyclock.weasley.config.security

import com.auth0.jwk.SigningKeyNotFoundException
import com.weasleyclock.weasley.config.exception.IdTokenEmptyException
import com.weasleyclock.weasley.config.exception.NotPostMethodException
import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorTypes
import com.weasleyclock.weasley.utils.JsonUtils
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
        } catch (e: IdTokenEmptyException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.A002)
        } catch (e: NotPostMethodException) {
            setErrorResponse(HttpStatus.NOT_FOUND, response, e, ErrorTypes.A003)
        } catch (e: SigningKeyNotFoundException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.A001)
        } catch (e: Exception) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.S001)
        }

    }

    private fun setErrorResponse(
        status: HttpStatus,
        response: HttpServletResponse,
        ex: Exception,
        errorTypes: ErrorTypes
    ) {

        response.reset()

        response.status = status.value()

        response.contentType = AppProperties.CONTENT_TYPE

        val detailMessage = ExceptionUtils.getStackTrace(ex)

        val errorDTO = ErrorDTO(errorTypes.code, errorTypes.message, detailMessage)

        try {
            response.writer.write(JsonUtils.convertObjectToJson(errorDTO));
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}

