package com.weasleyclock.weasley.service

import com.weasleyclock.weasley.config.exception.AppException
import com.weasleyclock.weasley.domain.Token
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

        val claims = JwtUtils.parseJwtToken(refreshToken, jwtKey!!, false)

        val id = (claims["id"] as Int).toLong()

        if (foundToken.userId != id) {
            throw AppException(ErrorTypes.NOT_MATCH_ID)
        }

        val email = claims["email"] as String

        val name = claims["name"] as String

        val authorities = claims["authorities"] as List<String>

        val accessToken = JwtUtils.createByAccessToken(jwtKey.toString(), id, email, name, authorities)

        val refreshToken = JwtUtils.createByRefreshToken(jwtKey.toString(), id, email, name, authorities)

        foundToken.token = refreshToken

        return UserDTO.AccessToken(accessToken, refreshToken)
    }

    fun createByToken(userId: Long?, refreshToken: String?) {

        val foundTokenOptional = tokenRepository.findByUserId(userId!!)

        if (foundTokenOptional.isPresent) {

            val foundToken = foundTokenOptional.get()

            foundToken.token = refreshToken

        } else {

            tokenRepository.save(Token(userId, refreshToken!!))

        }

    }

}
