package com.weasleyclock.weasleyclient.dto

import com.querydsl.core.annotations.QueryProjection
import java.math.BigDecimal

class BandOneDTO {

    var id: Long? = null
    var title: String? = null
    var userId: Long? = null
    var userName: String? = null
    var itemTitle: String? = null
    var latitude: BigDecimal? = null
    var longitude: BigDecimal? = null
    var isCurrent: Boolean? = null

    @QueryProjection
    constructor(
        id: Long,
        title: String,
        userId: Long,
        userName: String,
        itemTitle: String,
        latitude: BigDecimal,
        longitude: BigDecimal,
        isCurrent: Boolean
    ) {
        this.id = id
        this.title = title
        this.userId = userId
        this.userName = userName
        this.itemTitle = itemTitle
        this.latitude = latitude
        this.longitude = longitude
        this.isCurrent = isCurrent
    }
}

