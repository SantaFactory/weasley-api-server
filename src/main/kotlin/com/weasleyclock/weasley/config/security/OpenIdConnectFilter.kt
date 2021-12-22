package com.weasleyclock.weasley.config.security

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.UrlJwkProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.weasleyclock.weasley.config.exception.IdTokenEmptyException
import com.weasleyclock.weasley.config.exception.NotPostMethodException
import com.weasleyclock.weasley.domain.Auth
import com.weasleyclock.weasley.domain.User
import com.weasleyclock.weasley.enmus.AppRoles
import com.weasleyclock.weasley.enmus.UserTypes
import com.weasleyclock.weasley.repository.UserRepository
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
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

    private var userRepository: UserRepository? = null

    constructor(
        defaultFilterProcessesUrl: String,
        jwkUrl: String,
        userRepository: UserRepository,
        jwtKey: String
    ) : super(
        defaultFilterProcessesUrl
    ) {
        // NoAuth manager class
        authenticationManager = NoOpAuthenticationManager()
        setAuthenticationFailureHandler(DomainFailureHandler())
        setAuthenticationSuccessHandler(DomainSuccessHandler(jwtKey))
        this.jwkUrl = jwkUrl
        this.userRepository = userRepository
    }

    @Throws(Exception::class)
    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication? {

        val method = request!!.method

        if (HttpMethod.POST.name != method) {
            throw NotPostMethodException()
        }

        val requestBody = getByRequestBodyToMap(request)
        val idToken = requestBody["id_token"]
        val id = requestBody["id"]
        if (StringUtils.isNotEmpty(id)) {
            return getAdminAuthenticationToken(id!!)
        }

        if (idToken!!.isEmpty()) {
            throw IdTokenEmptyException()
        }

        val kid = JwtHelper.headers(idToken)["kid"] as String

        val tokenDecoded: org.springframework.security.jwt.Jwt? = JwtHelper.decodeAndVerify(idToken, verifier(kid))

        val authInfo: MutableMap<*, *>? = ObjectMapper().readValue(tokenDecoded?.claims, MutableMap::class.java)

        val userName = authInfo?.get("email") as String

        log.info { "login user $userName" }

        val userOptional = userRepository!!.findByEmail(userName)

        if (userOptional.isEmpty) {

            val name = authInfo?.get("name") as String

            val sub = authInfo?.get("sub") as String

            val newEntity = User(userName, name, UserTypes.GOOGLE, sub, linkedSetOf(Auth(AppRoles.USER.value)))

            userRepository!!.save(newEntity)

            val domainUserDetail = DomainUserDetail(newEntity)

            return UsernamePasswordAuthenticationToken(domainUserDetail, null, domainUserDetail.authorities)

        }

        val domainUserDetail = DomainUserDetail(userOptional.get())

        return UsernamePasswordAuthenticationToken(domainUserDetail, null, domainUserDetail.authorities)
    }

    private fun getAdminAuthenticationToken(id: String): UsernamePasswordAuthenticationToken {

        val adminUserOptional = userRepository!!.findByEmail(id)

        if (adminUserOptional.isEmpty) {
            throw UsernameNotFoundException("user not found name is $id")
        }

        val adminUser = adminUserOptional.get()

        val adminUserDetail = DomainUserDetail(adminUser)

        return UsernamePasswordAuthenticationToken(adminUserDetail, null, adminUserDetail.authorities)
    }

    /**
     * Get by request body to map </br>
     * @description 요청에서 From 으로 넘길 경우 데이터를 읽고 가지고는 메소드
     * @param request
     * @return
     */
    private fun getByRequestBodyToMap(request: HttpServletRequest?): Map<String, String> {

        val objectMapper = ObjectMapper()

        val requestBody = request!!.reader.lines().collect(Collectors.joining(System.lineSeparator()))

        return objectMapper.readValue(requestBody, Map::class.java) as Map<String, String>
    }

    /**
     * kid 체크로 google check 값 리턴
     *
     * @param kid
     * @return
     */
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
