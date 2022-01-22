package com.weasleyclock.weasley.dto.projection

import org.springframework.beans.factory.annotation.Value

interface IBandUserCount {

    fun getId(): Long
    fun getTitle(): String

    @Value("#{target.memberSet.size()}")
    fun getUserCount(): Long

}
