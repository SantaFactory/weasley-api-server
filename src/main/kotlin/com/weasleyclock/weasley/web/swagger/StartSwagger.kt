package com.weasleyclock.weasley.web.swagger

import com.weasleyclock.weasley.dto.AppMessageDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity

interface StartSwagger {

    @Operation(summary = "test api start", description = "swagger example")
    fun showByStart(): ResponseEntity<AppMessageDTO>

}
