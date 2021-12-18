package com.weasleyclock.weasley.config.security

import com.auth0.jwk.SigningKeyNotFoundException
import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorType
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
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorType.A002)
        } catch (e: NotPostMethodException) {
            setErrorResponse(HttpStatus.NOT_FOUND, response, e, ErrorType.A003)
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
        errorType: ErrorType
    ) {

        response.status = status.value()

        response.contentType = AppProperties.CONTENT_TYPE

        val detailMessage = ExceptionUtils.getStackTrace(ex)

        val errorDTO = ErrorDTO(errorType.code, errorType.message, detailMessage)

        try {
            response.writer.write(JsonUtils.convertObjectToJson(errorDTO));
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


}

