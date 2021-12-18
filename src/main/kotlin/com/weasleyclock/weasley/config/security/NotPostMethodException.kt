package com.weasleyclock.weasley.config.security

/**
 * Not post method exception
 * 포스트 메소드가 아닌 예외 처리
 * @constructor Create empty Not post method exception
 */
class NotPostMethodException : RuntimeException {
    constructor() : super("is not mapping post method")
}
