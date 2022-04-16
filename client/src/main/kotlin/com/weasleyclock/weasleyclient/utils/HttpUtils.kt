package com.weasleyclock.weasleyclient.utils

import com.weasleyclock.weasleyclient.dto.ErrorDTO
import com.weasleyclock.weasleyclient.enmus.ErrorTypes
import com.weasleyclock.weasleyclientclient.config.security.AppProperties
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.http.HttpStatus
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws

class HttpUtils {

    companion object {

        @Throws(IOException::class)
        fun setErrorResponse(
            status: HttpStatus,
            response: HttpServletResponse,
            ex: Exception,
            errorTypes: ErrorTypes
        ) {

            ex.printStackTrace()

            response.reset()

            response.status = status.value()

            response.contentType = AppProperties.CONTENT_TYPE

            val detailMessage = ExceptionUtils.getStackTrace(ex)

            val errorDTO = ErrorDTO(errorTypes.code, errorTypes.message, detailMessage)

            response.writer.write(JsonUtils.convertObjectToJson(errorDTO))

        }

    }

}
