package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/refresh-token")
class TokenResource(private val service: TokenService) {

    @PostMapping("")
    fun showByAccessToken(@RequestBody dto: Map<String, String>): ResponseEntity<AppMessageDTO> {
        val token = dto["token"] as String
        val body = AppMessageDTO(HttpStatus.OK.value(), service.issuedByAccessToken(token))
        return ResponseEntity
            .ok()
            .body(body)
    }

}
