package com.weasleyclock.weasleyclient.enmus

enum class BandRoleType : BaseEnum<String> {
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
