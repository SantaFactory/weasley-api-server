package com.weasleyclock.weasley.enmus

enum class AppRoles(value: String) {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    var value: String? = value

}
