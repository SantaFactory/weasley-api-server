package com.weasleyclock.weasley.config.security

import org.springframework.security.core.userdetails.User

class DomainUserDetail(user: com.weasleyclock.weasley.domain.User?) : User(user?.email, "", user?.getAuthorities()) {

    var id: Long? = null

    var name: String? = null

    var userKey: String? = null

    init {
        this.id = user?.id
        this.name = user?.name
        this.userKey = user?.userKey
    }

}
