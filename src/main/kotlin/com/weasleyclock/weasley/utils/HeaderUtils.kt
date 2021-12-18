package com.weasleyclock.weasley.utils

import com.weasleyclock.weasley.enmus.ApiTypes
import org.springframework.http.HttpHeaders
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

/**
 * Http Header Utils </br>
 * @author Gon-zo
 */
class HeaderUtils {

    companion object {

        /**
         * Create By Headers </br>
         * @param applicaiontName
         * @param message
         * @param param
         * @return HttpHeaders
         */
        private fun createByAlert(applicationName: String?, message: String, param: String): HttpHeaders {

            val headers = HttpHeaders()

            headers.add("X-$applicationName-alert", message)

            try {
                headers.add("X-$applicationName-param", URLEncoder.encode(param, StandardCharsets.UTF_8.toString()))
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return headers
        }

        private fun createByMessage(applicationName: String?, entityName: String, apiTypes: ApiTypes): String {

            val str = when (apiTypes) {
                ApiTypes.CREATE -> ".created"
                ApiTypes.UPDATE -> ".updated"
                ApiTypes.DELETE -> ".deleted"
                else -> ""
            }

            return "$applicationName, $entityName.$str"
        }

        /**
         * New Entity Http Header </br>
         * @param applicationName
         * @param entityName
         * @param param
         * @param apiTypes
         * @return HttpHeaders
         */
        fun createByAlert(
            applicationName: String?,
            entityName: String,
            param: String,
            apiTypes: ApiTypes
        ): HttpHeaders {

            val message = "$applicationName, $entityName.${createByMessage(applicationName, entityName, apiTypes)}"

            return createByAlert(applicationName, message, param)
        }

        /**
         * Failure Message Http Header </br>
         * @param applicationName
         * @param entityName
         * @param defaultMessage
         * @return HttpHeaders
         */
        fun createByFailureAlert(applicationName: String, entityName: String, defaultMessage: String): HttpHeaders {
            val headers = HttpHeaders()
            headers.add("X-$applicationName-error", defaultMessage)
            headers.add("X-$applicationName-params", entityName)
            return headers
        }

    }

}
