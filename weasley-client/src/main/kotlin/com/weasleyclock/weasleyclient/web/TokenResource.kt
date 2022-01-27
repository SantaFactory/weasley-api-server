package com.weasleyclock.weasleyclient.web

import com.weasleyclock.weasleyclient.dto.AppMessageDTO
import com.weasleyclock.weasleyclient.dto.TokenDTO
import com.weasleyclock.weasleyclient.service.TokenService
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
