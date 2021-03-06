package com.weasleyclock.weasleyclient.dto

import com.weasleyclock.weasleyclient.domain.User
import com.weasleyclock.weasleyclient.domain.Weasley
import java.math.BigDecimal

class WeasleyDTO {

    data class Current(val latitude: BigDecimal, val longitude: BigDecimal)

    data class Base(val title: String, val latitude: BigDecimal, val longitude: BigDecimal)

    data class Store(val weasleies: List<Base>) {
        fun toEntities(user: User): List<Weasley> =
            this.weasleies.map { weasley -> Weasley(user, weasley.title, weasley.latitude, weasley.longitude) }
    }

}
