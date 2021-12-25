package com.weasleyclock.weasley.web.docs

import com.weasleyclock.weasley.dto.AppMessageDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity

interface StartDocs {

    @Operation(summary = "test api start", description = "swagger example")
    fun showByStart(): ResponseEntity<AppMessageDTO>

}
