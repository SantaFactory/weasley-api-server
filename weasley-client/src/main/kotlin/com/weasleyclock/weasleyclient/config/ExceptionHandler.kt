package com.weasleyclock.weasleyclient.config

import com.weasleyclock.weasleyclient.dto.ErrorDTO
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import com.weasleyclock.weasleyclient.config.exception.AppException
import com.weasleyclock.weasleyclient.config.exception.NotFoundDataException
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse

/**
 * Exception handler
 * 예외 처리 핸들러
 * @constructor Create empty Exception handler
 */
@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: Exception, response: HttpServletResponse): ResponseEntity<ErrorDTO> =
        createByResponseBody(e, response, ErrorTypes.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR)

    @ResponseBody
    @ExceptionHandler(AppException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: AppException, response: HttpServletResponse): ResponseEntity<ErrorDTO> =
        createByResponseBody(e, response, e.getErrorTypes()!!, HttpStatus.INTERNAL_SERVER_ERROR)

    @ResponseBody
    @ExceptionHandler(NotFoundDataException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: NotFoundDataException, response: HttpServletResponse): ResponseEntity<ErrorDTO> =
        createByResponseBody(e, response, ErrorTypes.NOT_FOUND_ERROR, HttpStatus.NOT_FOUND)

    private fun createByResponseBody(
        e: Exception,
        response: HttpServletResponse,
        errorTypes: ErrorTypes,
        status: HttpStatus
    ): ResponseEntity<ErrorDTO> {

        val detailMessage = ExceptionUtils.getStackTrace(e)

        val body = ErrorDTO(errorTypes.code, errorTypes.message, detailMessage)

        e.printStackTrace()

        response.reset()

        return ResponseEntity.status(status.value()).body(body)
    }

}
