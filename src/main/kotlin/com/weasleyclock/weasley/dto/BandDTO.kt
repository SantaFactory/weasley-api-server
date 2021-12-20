package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.*
import org.springframework.beans.factory.annotation.Value
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
                    weasleyItem.title,
                    weasleyItem.latitude,
                    weasleyItem.longitude
                )
            }.toSet() as MutableSet<BandWeasley>
        }

    }

    data class Weasley(val title: String, val latitude: BigDecimal, val longitude: BigDecimal)

    data class Updated(val title: String, val bandUserSet: Set<BandUser>)

    data class Base(val id: Long, val title: String)

    data class UserInfo(val id: Long, val email: String, val role: String)

    interface OnlyBandUser {

        @Value("#{target.bandUserSet}")
        fun getBandUserSet(): MutableSet<BandUser>

        interface BandUser {
            fun getUser(): User
            fun getBandRole(): BandRole
        }

        interface User {
            fun getId(): Long
            fun getEmail(): String
        }

    }

}
