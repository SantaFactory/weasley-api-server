package com.weasleyclock.weasley.config.security

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.SigningKeyNotFoundException
import com.auth0.jwk.UrlJwkProvider
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.jwt.JwtHelper
import org.springframework.security.jwt.crypto.sign.RsaVerifier
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Open id connect filter
 * Google ID Token Filter
 * @constructor Create empty Open id connect filter
 */
class OpenIdConnectFilter : AbstractAuthenticationProcessingFilter {

    private val log = KotlinLogging.logger {}

    private var jwkUrl: String? = null

    constructor(defaultFilterProcessesUrl: String, jwkUrl: String) : super(defaultFilterProcessesUrl) {
        // NoAuth manager class
        authenticationManager = NoOpAuthenticationManager()
        setAuthenticationFailureHandler(DomainFailureHandler())
        this.jwkUrl = jwkUrl
    }

    @Throws(Exception::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {

        val requestBody = getByRequestBodyToMap(request)

        val idToken = requestBody["id_token"]

        val kid = JwtHelper.headers(idToken)["kid"] as String

        val tokenDecoded: org.springframework.security.jwt.Jwt? = JwtHelper.decodeAndVerify(idToken, verifier(kid))

        val authInfo: MutableMap<*, *>? = ObjectMapper().readValue(tokenDecoded?.claims, MutableMap::class.java)

        val userName = authInfo?.get("email")

        log.info { "login user $userName" }

        return this.authenticationManager.authenticate(UsernamePasswordAuthenticationToken(userName, null))
    }

    private fun getByRequestBodyToMap(request: HttpServletRequest?): Map<String, String> {

        val objectMapper = ObjectMapper()

        val requestBody = request!!.reader.lines().collect(Collectors.joining(System.lineSeparator()))

        return objectMapper.readValue(requestBody, Map::class.java) as Map<String, String>
    }

    @Throws(Exception::class)
    private fun verifier(kid: String): RsaVerifier? {
        val provider: JwkProvider = UrlJwkProvider(URL(jwkUrl))
        val jwk = provider[kid]
        return RsaVerifier(jwk.publicKey as RSAPublicKey)
    }

    /**
     * Verify claims
     *
     * @param claims
     * @param clientId
     * @param issuer
     */
    private fun verifyClaims(claims: Map<*, *>, clientId: String, issuer: String) {
        val exp = claims["exp"] as Int
        val expireDate = Date(exp * 1000L)
        val now = Date()
        if (expireDate.before(now) || claims["iss"] != issuer || claims["aud"] != clientId) {
            throw RuntimeException("Invalid claims")
        }
    }

}
