package com.weasleyclock.weasleyclient.config.security

import com.auth0.jwk.JwkProvider
import com.auth0.jwk.UrlJwkProvider
import com.fasterxml.jackson.databind.ObjectMapper
import com.weasleyclock.weasleyclient.service.TokenService
import com.weasleyclock.weasleyclient.service.UserService
import com.weasleyclock.weasleyclient.config.exception.IdTokenEmptyException
import com.weasleyclock.weasleyclient.config.exception.NotPostMethodException
import com.weasleyclock.weasleyclient.enmus.RoleType
import mu.KotlinLogging
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.jwt.JwtHelper
import org.springframework.security.jwt.crypto.sign.RsaVerifier
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Open id connect filter
 * Google ID Token Filter
 * @constructor Create empty Open id connect filter
 */
class OpenIdConnectFilter// NoAuth manager class
    (
    defaultFilterProcessesUrl: String,
    jwkUrl: String,
    userService: UserService,
    tokenService: TokenService,
    jwtKey: String
) : AbstractAuthenticationProcessingFilter(
    defaultFilterProcessesUrl
) {

    private val log = KotlinLogging.logger {}

    private var jwkUrl: String? = jwkUrl

    private var userService: UserService? = userService

    init {
        authenticationManager = NoOpAuthenticationManager()
        setAuthenticationFailureHandler(DomainFailureHandler())
        setAuthenticationSuccessHandler(DomainSuccessHandler(jwtKey, tokenService))
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

        val userOptional = userService!!.getByLoginUser(userName)

        if (userOptional.isEmpty) {

            val newEntity = userService!!.createByNewUser(authInfo)

            val authorities = listOf(SimpleGrantedAuthority(RoleType.ROLE_ADMIN.name))

            val domainUserDetail = DomainUserDetail(newEntity!!, authorities)

            return UsernamePasswordAuthenticationToken(domainUserDetail, null, domainUserDetail.authorities)

        }

        val user = userOptional.get()

        val authorities = listOf(SimpleGrantedAuthority(user.getRole()!!.name))

        val domainUserDetail = DomainUserDetail(user, authorities)

        return UsernamePasswordAuthenticationToken(domainUserDetail, null, domainUserDetail.authorities)
    }

    private fun getAdminAuthenticationToken(loginId: String): UsernamePasswordAuthenticationToken {

        val adminUserOptional = userService!!.getByLoginUser(loginId)

        if (adminUserOptional.isEmpty) {
            throw UsernameNotFoundException("user not found name is $loginId")
        }

        val adminUser = adminUserOptional.get()

        val adminUserDetail = DomainUserDetail(adminUser, listOf(SimpleGrantedAuthority(RoleType.ROLE_ADMIN.name)))

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
     * @param kid
     * @return
     */
    @Throws(Exception::class)
    private fun verifier(kid: String): RsaVerifier {
        val provider: JwkProvider = UrlJwkProvider(URL(jwkUrl))
        val jwk = provider[kid]
        return RsaVerifier(jwk.publicKey as RSAPublicKey)
    }

}
