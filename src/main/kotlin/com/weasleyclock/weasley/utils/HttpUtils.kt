package com.weasleyclock.weasley.utils

import com.weasleyclock.weasley.config.security.AppProperties
import com.weasleyclock.weasley.dto.ErrorDTO
import com.weasleyclock.weasley.enmus.ErrorTypes
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

            response.writer.write(JsonUtils.convertObjectToJson(errorDTO));

        }

    }

}
