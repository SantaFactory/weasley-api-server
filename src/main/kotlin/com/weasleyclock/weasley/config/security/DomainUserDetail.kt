package com.weasleyclock.weasley.config.security

import org.springframework.security.core.userdetails.User

class DomainUserDetail(user: com.weasleyclock.weasley.domain.User?) : User(user?.email, "", user?.getAuthorities()) {

    var user: com.weasleyclock.weasley.domain.User? = null

    init {
        this.user = user
    }

}
