package com.weasleyclock.weasleyclient.config.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class DomainUserDetail(user: com.weasleyclock.weasleyclient.domain.User?, authorities: List<GrantedAuthority>) :
    User(user?.getEmail(), "", authorities) {
    var user: com.weasleyclock.weasleyclient.domain.User? = null

    init {
        this.user = user
    }
}
