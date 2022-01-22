package com.weasleyclock.weasley.dto

import com.weasleyclock.weasley.domain.Band
import com.weasleyclock.weasley.domain.Member
import com.weasleyclock.weasley.domain.Weasley

class BandDTO {

    data class Created(
        val title: String, val weasley: Set<WeasleyDTO.Base>
    ) {

        fun toEntity(): Band {
            return Band(this.title)
        }

        fun toWeasleyItems(member: Member): MutableSet<Weasley> {
            return weasley.map { weasleyItem ->
                Weasley(
                    member.user, weasleyItem.title, weasleyItem.latitude, weasleyItem.longitude
                )
            }.toSet() as MutableSet<Weasley>
        }

    }

    data class Updated(val title: String)

}
