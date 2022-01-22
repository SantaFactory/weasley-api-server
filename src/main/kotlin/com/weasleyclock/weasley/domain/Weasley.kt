package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.base.BaseTimeEntity
import com.weasleyclock.weasley.domain.embedd.WeasleyKey
import org.apache.commons.lang3.ObjectUtils
import org.hibernate.Hibernate
import java.math.BigDecimal
import javax.persistence.*

@Table(name = "user_weasley")
@Entity
class Weasley() : BaseTimeEntity() {

    @EmbeddedId
    var id: WeasleyKey? = null

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @MapsId("userId")
    var user: User? = null

    // 위도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var latitude: BigDecimal? = null

    // 경도
    @Column(nullable = false, columnDefinition = "decimal(19,2) not null default 0")
    var longitude: BigDecimal? = null

    @Column(nullable = false)
    var isCurrent: Boolean? = null

    constructor(
        user: User?,
        title: String?,
        latitude: BigDecimal,
        longitude: BigDecimal
    ) : this() {
        this.user = user
        this.id = WeasleyKey(user!!.id!!, title!!)
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

    override fun hashCode(): Int {
        return id.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as Weasley
        return anyOther.id === this.id
    }

}

