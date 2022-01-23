package com.weasleyclock.weasley.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasley.domain.embedd.MemberKey
import org.hibernate.Hibernate
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "band_user")
class Member() {

    @EmbeddedId
    var id: MemberKey? = null

    @OneToOne
    var bandRole: BandRole? = null

    @ManyToOne
    @JsonIgnore
    @MapsId(value = "userId")
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @ManyToOne
    @JsonIgnore
    @MapsId(value = "bandId")
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    var band: Band? = null

    constructor(band: Band, user: User, bandRole: BandRole) : this() {
        this.band = band
        this.user = user
        this.bandRole = bandRole
        this.id = MemberKey(user.id!!, band.id!!)
    }

    constructor(band: Band, user: User, id: MemberKey, bandRole: BandRole) : this() {
        this.band = band
        this.user = user
        this.bandRole = bandRole
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (other == this) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as Member
        return anyOther.id!! == this.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
