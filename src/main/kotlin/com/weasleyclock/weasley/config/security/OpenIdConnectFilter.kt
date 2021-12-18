package com.weasleyclock.weasley.config.security

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.UrlJwkProvider
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.jwt.crypto.sign.RsaVerifier
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Open id connect filter
 * Google ID Token Filter
 * @constructor Create empty Open id connect filter
 */
class DomainAbstractAuthenticationProcessingFilter : AbstractAuthenticationProcessingFilter {

    constructor(defaultFilterProcessesUrl: String) : super(defaultFilterProcessesUrl){

    }

    @Value("\${google.jwkUrl}")
    private var jwkUrl: String? = null

    private val log = KotlinLogging.logger {}

//    override fun doFilterInternal(
//        request: HttpServletRequest,
//        response: HttpServletResponse,
//        filterChain: FilterChain
//    ) {
//        log.info { "filter success before" }
//
//        val url = request.requestURL
//
//        if ("/api/google-login".equals(url)) {
//
////            val idToken: String = accessToken.getAdditionalInformation().get("id_token").toString()
////            val kid: String = JwtHelper.headers(idToken).get("kid")
//
//            val idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjI1MmZjYjk3ZGY1YjZiNGY2ZDFhODg1ZjFlNjNkYzRhOWNkMjMwYzUiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiYXpwYmxhaGJsYWguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiJhdWRibGFobGJsYWguYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiI4OTg5ODk4OTg5ODk4OTg5IiwiZW1haWwiOiJ0ZWlob25nOTNAZ21haWwuY29tIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJwdC1kc2thLTJkYWsiLCJuYW1lIjoiVGVpSG9uZyIsInBpY3R1cmUiOiJodHRwczovL2xoMy5nb29nbGV1c2VyY29udGVudC5jb20vcGljcGljcGljIiwiZ2l2ZW5fbmFtZSI6IlRlaSIsImxvY2FsZSI6ImtvIiwiaWF0IjoxNjEwMDQyMjIyLCJleHAiOjE2MTAwNDIyMjIsImp0aSI6IjgyMjIyMjIxMjMzMmRhc2RhYWFhYWRjIn0.AJ7KtwszbfzRJ6TAB3yzEn1TD9AhVNrM3v532yJcXVF8AGqIfYlr-JQTyD-u5lR8uwse79l9d1s7ualTtErKebBcXz0OJT1R19Cey4UKNs_xFKxQXVEd-TmPBoCzs0GKQxqR4APIu5g71oubcN6PcHeWZAqxr9_Vqx_rzQ2aFQ5G1DE8vX05G9sXot2uXewK_0e2cN8sXdBbUuFTFjyGhOel9u_GUWdIlQfwHiOCdjLn2QD3KBYOMW7KshxW4fYk8dXtWZM5RtcBXCUSd_k-kfJhaBaXQNRUcqkTzAR5kUGqw95Cdj04FVn1uG1BpMYjpsytIZHFqTJhlRQ-YjmRwA"
//
//            val kid  =  ""
//
//            val tokenDecoded: org.springframework.security.jwt.Jwt? = JwtHelper.decodeAndVerify(idToken, verifier(kid))
//
//            val authInfo: MutableMap<*, *>? = ObjectMapper().readValue(tokenDecoded?.claims, MutableMap::class.java)
//
//        }
//
//        filterChain.doFilter(request, response)
//
//        log.info { "filter success after" }
//    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        log.info { "test success" }
        return UsernamePasswordAuthenticationToken("", null, null)
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
