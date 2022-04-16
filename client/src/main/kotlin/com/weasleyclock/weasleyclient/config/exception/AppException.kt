package com.weasleyclock.weasleyclient.config.exception

import com.weasleyclock.weasleyclient.enmus.ErrorTypes


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
