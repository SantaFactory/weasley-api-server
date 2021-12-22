package com.weasleyclock.weasley.enmus

enum class ErrorTypes(val code: String, val message: String) {

    INTERNAL_SERVER_ERROR("S001", "internal server error"),

    // 인증 관련
    NOT_FOUND_KEY("A001", "not found google decode key"),
    IS_EMPTY_TOKEN("A002", "is_token is empty"),
    NOT_USE_POST_METHOD("A003", "is login process use to post method"),

    // Member 관련 Error
    IS_NOT_READER("M001", "is not reader")

}
