package com.weasleyclock.weasley.enmus

enum class ErrorTypes(val code: String, val message: String) {

    S001("S001", "internal server error"),

    // 인증 관련
    A001("A001", "not found google decode key"),
    A002("A002", "is_token is empty"),
    A003("A003", "is login process use to post method"),

    // Member 관련 Error
    IS_NOT_READER("M001", "is not reader")

}
