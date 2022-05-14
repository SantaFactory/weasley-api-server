package com.weasleyclock.weasleyclient.enmus

enum class RoleName : BaseEnum<String> {
    LEADER {
        override fun getValue(): String = this.name
    },
    SUB_LEADER {
        override fun getValue(): String = this.name
    },
    MEMBER {
        override fun getValue(): String = this.name
    }
}
