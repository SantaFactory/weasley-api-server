package com.weasleyclock.weasleyclient.config.exception

import kotlin.RuntimeException as RuntimeException

/**
 * Not found data exception
 * 데이터 없을 때 사용하는 예외
 * @constructor Create empty Not found data exception
 */
class NotFoundDataException : RuntimeException {

    private var entityName: String? = null

    constructor()

    constructor(message: String) : super(message) {
    }

    constructor(entityName: String, message: String) : super(message) {
        this.entityName = entityName
    }

    fun getEntityName() = entityName
}
