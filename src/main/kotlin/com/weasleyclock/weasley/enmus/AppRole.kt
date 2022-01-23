package com.weasleyclock.weasley.enmus

enum class AppRole(value: String) {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    var value: String? = value

}
