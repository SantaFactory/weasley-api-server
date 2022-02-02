package com.weasleyclock.weasleyclient.dto

import com.weasleyclock.weasleyclient.domain.Band
import com.weasleyclock.weasleyclient.domain.Member
import com.weasleyclock.weasleyclient.domain.Weasley
import java.math.BigDecimal

class BandDTO {

    data class Created(
        val title: String, val weasley: Set<WeasleyDTO.Base>
    ) {
        fun toEntity(): Band {
            return Band(this.title)
        }
    }

    data class Updated(val title: String)

    data class GroupingKey(val id: Long, val title: String)

    data class WeasleyItem(
        val userId: Long?,
        val title: String?,
        var name: String?,
        val latitude: BigDecimal?,
        val longitude: BigDecimal?,
        val isCurrent: Boolean?
    )

    data class Grouping(val id: Long, val title: String) {

        var weasleyItemList: List<WeasleyItem>? = null

        fun toCreateGrouping(itemList: List<BandOneDTO>): Grouping {
            this.weasleyItemList = toConvertItems(itemList)
            return this
        }

        private fun toConvertItems(itemList: List<BandOneDTO>) =
            itemList.map {
                WeasleyItem(it.userId, it.itemTitle, it.userName, it.latitude, it.longitude, it.isCurrent)
            }.toList()

        override fun hashCode(): Int = id.toInt()
    }


}
