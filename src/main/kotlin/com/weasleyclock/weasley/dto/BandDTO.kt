package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.BandUser
import com.weasleyclock.weasley.domain.BandWeasley
import java.math.BigDecimal

class BandDTO {

    data class Created(
        val title: String,
        val weasley: Set<Weasley>
    ) {

        fun toEntity(): Band {
            return Band(this.title)
        }

        fun toWeasleyItems(bandUser: BandUser): MutableSet<BandWeasley> {
            return weasley.map { weasleyItem ->
                BandWeasley(
                    bandUser.band,
                    bandUser.user,
                    bandUser.id,
                    weasleyItem.title,
                    weasleyItem.latitude,
                    weasleyItem.longitude
                )
            }.toSet() as MutableSet<BandWeasley>
        }

    }

    data class Weasley(val title: String, val latitude: BigDecimal, val longitude: BigDecimal)

    data class Updated(val title: String, val bandUserSet: Set<BandUser>)

}
