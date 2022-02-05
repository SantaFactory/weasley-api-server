package com.weasleyclock.weasleyclient.config.security

import com.auth0.jwk.SigningKeyNotFoundException
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import com.weasleyclock.weasleyclient.utils.HttpUtils.Companion.setErrorResponse
import com.weasleyclock.weasleyclient.config.exception.IdTokenEmptyException
import com.weasleyclock.weasleyclient.config.exception.NotPostMethodException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
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

        val contentCachingRequestWrapper = ContentCachingRequestWrapper(request)

        val contentCachingResponseWrapper = ContentCachingResponseWrapper(response)

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper)

        contentCachingResponseWrapper.copyBodyToResponse()

    }

}

