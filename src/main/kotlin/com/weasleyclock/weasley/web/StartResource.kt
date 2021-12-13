package com.weasleyclock.weasley.web

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class StartResource {

    @Operation(summary = "test api start", description = "swagger example")
    @GetMapping("/start")
    fun showByStart(): ResponseEntity<String> {
        return ResponseEntity.ok("start")
    }

}
