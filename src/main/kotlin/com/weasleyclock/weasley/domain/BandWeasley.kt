package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.BandUserKey
import java.math.BigDecimal
import javax.persistence.*

@Table
@Entity
data class BandWeasley(
    @EmbeddedId
    var id: BandUserKey? = null,
    @Column(nullable = false)
    var title: String? = null,
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId("userId")
    var user: User? = null,
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    @MapsId("bandId")
    var band: Band? = null,
    // 위도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var latitude: BigDecimal? = null,
    // 경도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var longitude: BigDecimal? = null
) : BaseEntity() {

    constructor(
        band: Band?,
        user: User?,
        id: BandUserKey?,
        title: String,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.band = band
        this.user = user
//        this.id = id
        this.title = title
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(
        id: BandUserKey?,
        title: String,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.id = id
        this.title = title
        this.latitude = latitude
        this.longitude = longitude
    }

}
