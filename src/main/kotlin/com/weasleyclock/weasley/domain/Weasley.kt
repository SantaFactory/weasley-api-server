package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.WeasleyKey
import org.apache.commons.lang3.ObjectUtils
import java.math.BigDecimal
import javax.persistence.*

@Table(name = "band_weasley")
@Entity
class Weasley() : BaseEntity() {

    @EmbeddedId
    var id: WeasleyKey? = null

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
    var isCurrent: Boolean? = null

    constructor(
        band: Band?,
        user: User?,
        title: String?,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.band = band
        this.user = user
        this.id = WeasleyKey(user!!.id!!, band!!.id!!, title!!)
        this.latitude = latitude
        this.longitude = longitude
    }

    constructor(
        id: WeasleyKey?,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.id = id
        this.latitude = latitude
        this.longitude = longitude
    }

    @PrePersist
    fun prePersist() {
        if (ObjectUtils.isEmpty(isCurrent)) {
            isCurrent = false
        }
    }

    @PreUpdate
    fun preUpdate() {
        if (ObjectUtils.isEmpty(isCurrent)) {
            isCurrent = false
        }
    }

}

