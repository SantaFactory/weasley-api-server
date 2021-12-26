package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.BandWeasleyKey
import java.math.BigDecimal
import javax.persistence.*

@Table
@Entity
class BandWeasley() : BaseEntity() {

    @EmbeddedId
    var id: BandWeasleyKey? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId("userId")
    var user: User? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    @MapsId("bandId")
    var band: Band? = null

    // 위도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var latitude: BigDecimal? = null

    // 경도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var longitude: BigDecimal? = null

    @Column(nullable = false)
    var isCurrent : Boolean ? = null

    constructor(
        band: Band?,
        user: User?,
        title: String?,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.band = band
        this.user = user
        this.id = BandWeasleyKey(user!!.id!!, band!!.id!!, title!!)
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(
        id: BandWeasleyKey?,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.id = id
        this.latitude = latitude
        this.longitude = longitude
    }

}

