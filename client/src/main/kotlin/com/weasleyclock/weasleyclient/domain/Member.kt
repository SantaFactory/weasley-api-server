package com.weasleyclock.weasleyclient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasleyclient.domain.embedd.MemberKey
import com.weasleyclock.weasleyclient.enmus.BandRoleType
import org.hibernate.Hibernate
import org.hibernate.annotations.BatchSize
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "band_user")
data class Member(
    @EmbeddedId
    private var id: MemberKey,
    private var role: BandRoleType,
    @JsonIgnore
    @BatchSize(size = 10)
    @MapsId(value = "userId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private var user: User?,
    @JsonIgnore
    @BatchSize(size = 10)
    @MapsId(value = "bandId")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "band_id", referencedColumnName = "id")
    private var band: Band? = null
) {

    constructor(band: Band, user: User, role: BandRoleType) : this(
        MemberKey(user.getId(), band.getId()), role, user, band
    )

    constructor(band: Band, user: User, id: MemberKey, role: BandRoleType) : this(id, role, user, band)

    constructor(userId: Long, bandId: Long, role: BandRoleType) : this(MemberKey(userId, bandId), role, null, null)

    override fun equals(other: Any?): Boolean {
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as Member
        return anyOther.id!! == this.id
    }

    fun getBandRole() = this.role

    fun getId() = this.id

    @Transient
    fun eqUserId(userId: Long) = userId == this.id!!.getUserId()

    @Transient
    fun notEqUserId(userId: Long) = userId !== this.id!!.getUserId()


    @Transient
    fun changeBandRole(role: BandRoleType) {
        this.role = role
    }

    @Transient
    fun isLeader() = BandRoleType.LEADER == this.role
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + role.hashCode()
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (band?.hashCode() ?: 0)
        return result
    }
}
