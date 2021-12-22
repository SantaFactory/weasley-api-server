package com.weasleyclock.weasley.config.exception

import com.weasleyclock.weasley.enmus.ErrorTypes

class AppException : RuntimeException {

    private var errorTypes: ErrorTypes? = null

    constructor(errorTypes: ErrorTypes) : super() {
        this.errorTypes = errorTypes
    }

    constructor(errorTypes: ErrorTypes, message: String) : super(message) {
        this.errorTypes = errorTypes
    }

    constructor() : super("")

    fun getErrorTypes(): ErrorTypes? = errorTypes

}
