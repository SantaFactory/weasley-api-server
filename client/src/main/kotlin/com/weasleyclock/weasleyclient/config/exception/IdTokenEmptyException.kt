package com.weasleyclock.weasleyclient.config.exception

import java.lang.RuntimeException

class IdTokenEmptyException : RuntimeException {
    constructor() : super("id_token is empty to request body")
}
