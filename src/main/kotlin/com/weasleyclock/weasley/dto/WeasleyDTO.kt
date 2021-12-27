package com.weasleyclock.weasley.dto

import java.math.BigDecimal

class WeasleyDTO {

    data class Current(val latitude: BigDecimal, val longitude: BigDecimal)

    data class Base(val title: String, val latitude: BigDecimal, val longitude: BigDecimal)

}
