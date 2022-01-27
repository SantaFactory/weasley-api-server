package com.weasleyclock.weasleyclient.config.security

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication

class NoOpAuthenticationManager : AuthenticationManager {
    override fun authenticate(authentication: Authentication?): Authentication? {
        return authentication
    }
}
