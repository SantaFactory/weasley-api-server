package com.weasleyclock.weasleyclient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasleyclient.domain.embedd.MemberKey
import com.weasleyclock.weasleyclient.enmus.RoleName
import org.hibernate.Hibernate
import org.hibernate.annotations.BatchSize
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

    @JsonIgnore
    @BatchSize(size = 10)
    @MapsId(value = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @JsonIgnore
    @BatchSize(size = 10)
    @MapsId(value = "bandId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    var band: Band? = null

    constructor(band: Band, user: User, bandRole: BandRole) : this() {
        this.band = band
        this.user = user
        this.bandRole = bandRole
        this.id = MemberKey(user.id!!, band.getId())
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

    @Transient
    fun changeBandRole(roleName: RoleName) {
        this.bandRole = BandRole(roleName)
    }

    @Transient
    fun isLeader() = RoleName.LEADER == this.bandRole!!.getTitle()

}
