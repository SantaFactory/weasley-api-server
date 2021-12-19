package com.weasleyclock.weasley.config.security

import org.springframework.security.core.userdetails.User

class DomainUserDetail : User {

    var userKey: String? = null

    constructor(user: com.weasleyclock.weasley.domain.User) : super(user.email, null, user.getAuthorities()) {
        this.userKey = user.userKey
    }

}
