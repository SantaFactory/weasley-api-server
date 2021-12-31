package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.dto.UserDTO
import com.weasleyclock.weasley.enmus.ErrorTypes
import com.weasleyclock.weasley.repository.TokenRepository
import com.weasleyclock.weasley.utils.JwtUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(rollbackFor = [Exception::class])
class TokenService(private val tokenRepository: TokenRepository) {

    @Value("\${jwt.key}")
    private var jwtKey: String? = null

    @Transactional
    fun issuedByAccessToken(refreshToken: String): UserDTO.AccessToken {

        val foundToken =
            tokenRepository.findByToken(refreshToken).orElseThrow { AppException(ErrorTypes.NOT_FOUND_TOKEN) }

        val claims = JwtUtils.parseJwtToken(refreshToken, jwtKey!!)

        val id = (claims["id"] as Int).toLong()

        if (foundToken.userId != id) {
            throw AppException(ErrorTypes.NOT_MATCH_ID)
        }

        var email = claims["email"] as String

        var name = claims["name"] as String

        var authorities = claims["authorities"] as List<String>

        var accessToken = JwtUtils.createByAccessToken(jwtKey.toString(), id, email, name, authorities)

        var refreshToken = JwtUtils.createByRefreshToken(jwtKey.toString(), id, email, name, authorities)

        foundToken.token = refreshToken

        return UserDTO.AccessToken(accessToken, refreshToken)
    }

}
