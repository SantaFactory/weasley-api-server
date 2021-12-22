package com.weasleyclock.weasley.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Header
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.ObjectUtils
import java.time.Duration
import java.util.*

class JwtUtils {

    companion object {

        private const val issuer: String = "fresh"

        private const val HEADER_PREFIX: String = "Bearer "

        /**
         * JWT 토큰 만들기
         *
         * @param secretKey
         * @param id
         * @param email
         * @param name
         * @return String
         */
        fun makeByJwtToken(
            secretKey: String,
            id: Long?,
            email: String?,
            name: String?
        ): String? {
            val now = Date()
            return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(Date(now.time + Duration.ofMinutes(30).toMillis()))
                .claim("id", id)
                .claim("email", email)
                .claim("name", name)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact()
        }

        /**
         * Parse jwt token
         *
         * @param authorizationHeader
         * @param secretKey
         * @return
         */
        fun parseJwtToken(authorizationHeader: String, secretKey: String): Claims {
            validationAuthorizationHeader(authorizationHeader)
            val token = extractToken(authorizationHeader)
            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJwt(token)
                .body
        }

        /**
         * JWT 값 맞는지 체크 유무
         * @param header
         */
        private fun validationAuthorizationHeader(header: String) {
            if (ObjectUtils.isEmpty(header) || !header.startsWith(HEADER_PREFIX)) {
                throw IllegalArgumentException()
            }
        }

        /**
         * Extract Token
         * Bearer 구문 제거 하고 토큰 만들기
         * @param header
         * @return
         */
        private fun extractToken(header: String): String = header.substring(HEADER_PREFIX.length)

    }

}
