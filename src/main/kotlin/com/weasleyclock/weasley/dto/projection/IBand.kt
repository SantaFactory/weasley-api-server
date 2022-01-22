package com.weasleyclock.weasley.dto.projection

import org.springframework.beans.factory.annotation.Value
import java.math.BigDecimal

interface IBand {

    fun getId(): Long
    fun getTitle(): String

    @Value("#{target.weasleySet}")
    fun getWeasley(): MutableSet<UserNameAndPosition>

    interface UserNameAndPosition {
        fun getLatitude(): BigDecimal
        fun getLongitude(): BigDecimal
        fun getIsCurrent(): Boolean

        @Value("#{target.id.title}")
        fun getWeasleyTitle(): String

        @Value("#{target.user.name}")
        fun getUserName(): String
    }

}
