package com.weasleyclock.weasley.config.security

import org.springframework.security.core.userdetails.User

class DomainUserDetail : User {

    var id: Long? = null

    var name: String? = null

    var userKey: String? = null

    constructor(user: com.weasleyclock.weasley.domain.User) : super(user.email, null, user.getAuthorities()) {
        this.id = user.id
        this.name = user.name
        this.userKey = user.userKey
    }

}
