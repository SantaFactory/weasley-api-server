package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.*
import org.springframework.beans.factory.annotation.Value
import java.math.BigDecimal

class BandDTO {

    data class Created(
        val title: String, val weasley: Set<Weasley>
    ) {

        fun toEntity(): Band {
            return Band(this.title)
        }

        fun toWeasleyItems(member: Member): MutableSet<BandWeasley> {
            return weasley.map { weasleyItem ->
                BandWeasley(
                    member.band, member.user, weasleyItem.title, weasleyItem.latitude, weasleyItem.longitude
                )
            }.toSet() as MutableSet<BandWeasley>
        }

    }

    data class Weasley(val title: String, val latitude: BigDecimal, val longitude: BigDecimal)

    data class Updated(val title: String)

    interface OnlyBandUser {

        @Value("#{target.memberSet}")
        fun getMembers(): MutableSet<UserAndBandRole>

        interface UserAndBandRole {

            @Value("#{target.user.id}")
            fun getUserId(): Long

            @Value("#{target.user.email}")
            fun getEmail(): String

            @Value("#{target.bandRole.title}")
            fun getBandRole(): String
        }

    }

    interface BandUserCount {
        fun getId(): Long
        fun getTitle(): String

        @Value("#{target.memberSet.size()}")
        fun getUserCount(): Long
    }

    interface BandOne {
        fun getId(): Long
        fun getTitle(): String

        @Value("#{target.bandWeasleySet}")
        fun getWeasley(): MutableSet<UserNameAndPosition>

        interface UserNameAndPosition {
            fun getLatitude(): BigDecimal
            fun getLongitude(): BigDecimal
            fun getIsCurrent(): Boolean

            @Value("#{target.user.name}")
            fun getUserName(): String
        }
    }

}
