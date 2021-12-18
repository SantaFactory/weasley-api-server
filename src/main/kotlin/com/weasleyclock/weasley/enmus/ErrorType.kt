package com.weasleyclock.weasley.enmus

enum class ErrorType(val code: String, val message: String) {

    S001("S001", "internal server error"),

    // 인증 관련
    A001("A001", "not found google decode key")

}
