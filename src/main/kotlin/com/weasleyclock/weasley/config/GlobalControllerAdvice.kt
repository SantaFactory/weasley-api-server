package com.weasleyclock.weasley.config

import com.weasleyclock.weasley.dto.ErrorDTO
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
        val message = e.message
        val dto = ErrorDTO("server error", message, "default message")
        return ResponseEntity.ok(dto)
    }

}
