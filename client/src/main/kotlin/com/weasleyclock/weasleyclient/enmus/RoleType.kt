package com.weasleyclock.weasleyclient.enmus

enum class RoleType : BaseEnum<String> {
    ROLE_USER {
        override fun getValue(): String = this.name
    },
    ROLE_ADMIN {
        override fun getValue(): String = this.name
    }
}
