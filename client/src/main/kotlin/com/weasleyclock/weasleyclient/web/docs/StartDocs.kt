package com.weasleyclock.weasleyclient.web.docs

import com.weasleyclock.weasleyclient.dto.AppMessageDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity

interface StartDocs {

    @Operation(summary = "test api start", description = "swagger example")
    fun showByStart(): ResponseEntity<AppMessageDTO>

}
