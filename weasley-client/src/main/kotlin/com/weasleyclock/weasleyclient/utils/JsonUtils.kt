package com.weasleyclock.weasleyclient.utils

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper

class JsonUtils {

    companion object {

        /**
         * Convert object to json
         *
         * @param any
         * @return
         */
        @Throws(JsonProcessingException::class)
        fun convertObjectToJson(any: Any?): String {
            if (any == null) {
                return ""
            }
            val mapper = ObjectMapper()
            return mapper.writeValueAsString(any)
        }

    }
}
