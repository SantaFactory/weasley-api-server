package com.weasleyclock.weasley.web

import com.weasleyclock.weasley.dto.AppMessageDTO
import com.weasleyclock.weasley.dto.TokenDTO
import com.weasleyclock.weasley.service.TokenService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/refresh-token")
class TokenResource(private val service: TokenService) {

    @PostMapping("")
    fun showByAccessToken(@RequestBody dto: TokenDTO.RefreshToken): ResponseEntity<AppMessageDTO> {
        val body = AppMessageDTO(HttpStatus.OK.value(), service.issuedByAccessToken(dto.refreshToken))
        return ResponseEntity
            .ok()
            .body(body)
    }

}
