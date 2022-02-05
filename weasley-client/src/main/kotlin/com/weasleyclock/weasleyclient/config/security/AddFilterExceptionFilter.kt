package com.weasleyclock.weasleyclient.config.security

import com.auth0.jwk.SigningKeyNotFoundException
import com.weasleyclock.weasleyclient.config.exception.IdTokenEmptyException
import com.weasleyclock.weasleyclient.config.exception.NotPostMethodException
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import io.jsonwebtoken.UnsupportedJwtException
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.weasleyclock.weasleyclient.utils.HttpUtils.Companion.setErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.http.HttpStatus

class AddFilterExceptionFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            filterChain.doFilter(request, response)
        }
        catch (e: IdTokenEmptyException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.IS_EMPTY_TOKEN)
        } catch (e: NotPostMethodException) {
            setErrorResponse(HttpStatus.NOT_FOUND, response, e, ErrorTypes.NOT_USE_POST_METHOD)
        } catch (e: SigningKeyNotFoundException) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.NOT_FOUND_KEY)
        } catch (e: UnsupportedJwtException) {
            // 예상하는 형식과 다른 형식이거나 구성의 JWT일 때
            setErrorResponse(HttpStatus.FORBIDDEN, response, e, ErrorTypes.JWT_UNSUPPORTED)
        } catch (e: MalformedJwtException) {
            // JWT가 올바르게 구서오디지 않았을 때
            setErrorResponse(HttpStatus.FORBIDDEN, response, e, ErrorTypes.JWT_MALFORMED)
        } catch (e: ExpiredJwtException) {
            // JWT를 생성할 때 지정한 유효기간이 초과되었을 때
            setErrorResponse(HttpStatus.GONE, response, e, ErrorTypes.JWT_EXPIRED)
        } catch (e: SignatureException) {
            // JWT의 기존 서명을 확인하지 못했을 때
            setErrorResponse(HttpStatus.GONE, response, e, ErrorTypes.JWT_SIGNATURE)
        } catch (e: Exception) {
            setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e, ErrorTypes.INTERNAL_SERVER_ERROR)
        }

    }

}
