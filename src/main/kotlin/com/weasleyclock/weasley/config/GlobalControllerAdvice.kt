package com.weasleyclock.weasley.config

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

    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: Exception, response: HttpServletResponse): ResponseEntity<ErrorDTO> {

        response.reset()

        val detailMessage = ExceptionUtils.getStackTrace(e)

        val dto = ErrorDTO(
            ErrorTypes.S001.code, ErrorTypes.S001.message, detailMessage
        )

        e.printStackTrace()

        return ResponseEntity.ok(dto)
    }

}
