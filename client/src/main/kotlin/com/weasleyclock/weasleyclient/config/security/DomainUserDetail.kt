package com.weasleyclock.weasleyclient.config.security

import org.springframework.security.core.userdetails.User

class DomainUserDetail(user: com.weasleyclock.weasleyclient.domain.User?) :
    User(user?.getEmail(), "", user?.getAuthorities()) {

    var user: com.weasleyclock.weasleyclient.domain.User? = null

    init {
        this.user = user
    }

}
