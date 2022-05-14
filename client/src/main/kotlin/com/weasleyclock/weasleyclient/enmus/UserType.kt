package com.weasleyclock.weasleyclient.enmus

enum class UserType(var code: String, var desc: String) : BaseEnum<String> {
    DEFAULT("D", "application user") {
        override fun getValue(): String = this.code
    },
    GOOGLE("G", "google user") {
        override fun getValue(): String = this.code
    }
}
