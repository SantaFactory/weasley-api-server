package com.weasleyclock.weasley.enmus

enum class AppRoles {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    var value: String? = null

    constructor(value: String) {
        this.value = value
    }

}
