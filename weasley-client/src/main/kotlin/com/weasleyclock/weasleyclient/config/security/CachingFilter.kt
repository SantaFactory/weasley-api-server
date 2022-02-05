package com.weasleyclock.weasleyclient.config.security

import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class CachingFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
    ) {

        val contentCachingRequestWrapper = ContentCachingRequestWrapper(request)

        val contentCachingResponseWrapper = ContentCachingResponseWrapper(response)

        filterChain.doFilter(contentCachingRequestWrapper, contentCachingResponseWrapper)

        contentCachingResponseWrapper.copyBodyToResponse()
    }

}

