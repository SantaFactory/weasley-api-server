package com.weasleyclock.weasleyclient.enmus

enum class ErrorTypes(val code: String, val message: String) {

    INTERNAL_SERVER_ERROR("S001", "internal server error"),
    NOT_FOUND_ERROR("S002", "data not found server error"),

    // 인증 관련
    NOT_FOUND_KEY("A001", "not found google decode key"),
    IS_EMPTY_TOKEN("A002", "is_token is empty"),

    // refresh token
    NOT_MATCH_ID("A003", "not match id value"),
    NOT_FOUND_TOKEN("A004", "not found Token"),

    NOT_USE_POST_METHOD("A005", "is login process use to post method"),
    NOT_LOGIN_USER("A006", "is login process use to post method"),

    // Member 관련 Error
    IS_NOT_READER("M001", "is not reader"),

    // jwt 관련
    JWT_UNSUPPORTED("J001", "jwt not supported"),
    JWT_MALFORMED("J002", "jwt malformed"),
    JWT_EXPIRED("J003", "jwt expired"),

    JWT_SIGNATURE("J003", "jwt signature"),

}
