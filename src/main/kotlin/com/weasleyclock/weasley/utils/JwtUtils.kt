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

        fun createByAccessToken(
            secretKey: String,
            id: Long?,
            email: String?,
            name: String?,
            authorities: List<String>?
        ): String? = createByToken(secretKey, id, email, name, authorities, Date(Date().time + Duration.ofMinutes(30).toMillis()))

        fun createByRefreshToken(
            secretKey: String,
            id: Long?,
            email: String?,
            name: String?,
            authorities: List<String>?
        ): String? = createByToken(secretKey, id, email, name, authorities, Date(Date().time + Duration.ofMinutes(60).toMillis()))

        /**
         * JWT 토큰 만들기
         *
         * @param secretKey
         * @param id
         * @param email
         * @param name
         * @return String
         */
        private fun createByToken(
            secretKey: String,
            id: Long?,
            email: String?,
            name: String?,
            authorities: List<String>?,
            expiration: Date?
        ): String? {
            val now = Date()
            return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setIssuer(issuer)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .claim("id", id)
                .claim("email", email)
                .claim("name", name)
                .claim("authorities", authorities)
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
        fun parseJwtToken(authorizationHeader: String, secretKey: String, isAccess: Boolean): Claims {

            var passerToken = ""

            if (isAccess) {
                validationAuthorizationHeader(authorizationHeader)
                passerToken = extractToken(authorizationHeader)
            } else {
                passerToken = authorizationHeader
            }

            return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(passerToken)
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
