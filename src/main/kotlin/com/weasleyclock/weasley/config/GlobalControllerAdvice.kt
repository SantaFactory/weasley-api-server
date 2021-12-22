package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorTypes
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletResponse

/**
 * Global Exception Handler to Controller Layer
 */
@ControllerAdvice
class GlobalControllerAdvice {

    /**
     * Controller advice By Exception </br>
     * @param e
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: Exception, response: HttpServletResponse): ResponseEntity<ErrorDTO> =
        createByResponseBody(e, response, ErrorTypes.INTERNAL_SERVER_ERROR)

    /**
     * Controller advice By AppException </br>
     * @param e
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(AppException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: AppException, response: HttpServletResponse): ResponseEntity<ErrorDTO> =
        createByResponseBody(e, response, e.getErrorTypes()!!)


    /**
     * Create by Error response body </br>
     * @param e
     * @param response
     * @param errorTypes
     * @return
     */
    private fun createByResponseBody(
        e: Exception,
        response: HttpServletResponse,
        errorTypes: ErrorTypes
    ): ResponseEntity<ErrorDTO> {

        val detailMessage = ExceptionUtils.getStackTrace(e)

        val body = ErrorDTO(errorTypes.code, errorTypes.message, detailMessage)

        e.printStackTrace()

        response.reset()

        return ResponseEntity
            .ok()
            .body(body)
    }

}
