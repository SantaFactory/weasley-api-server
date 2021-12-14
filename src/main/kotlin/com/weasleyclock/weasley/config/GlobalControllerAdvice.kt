package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorType
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Global Exception Handler to Controller Layer
 */

@ControllerAdvice
class GlobalControllerAdvice {

    @ResponseBody
    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun controllerAdvice(e: Exception): ResponseEntity<ErrorDTO> {

        e.printStackTrace()

        val detailMessage = ExceptionUtils.getStackTrace(e)

        val dto = ErrorDTO(
            ErrorType.S001.code, ErrorType.S001.message, detailMessage
        )

        return ResponseEntity.ok(dto)
    }

}
