package com.weasleyclock.weasleyclient.domain.embedd

import org.hibernate.Hibernate
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class MemberKey : Serializable {

    @Transient
    private val serialVersionUID = 1L

    @Column
    private var userId: Long? = null

    @Column
    private var bandId: Long? = null

    constructor()

    constructor(userId: Long) {
        this.userId = userId
    }

    constructor(userId: Long?, bandId: Long?) {
        this.userId = userId
        this.bandId = bandId
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as MemberKey
        return anyOther.bandId == this.bandId && anyOther.userId == this.userId
    }

    override fun hashCode(): Int {
        return userId!!.toInt() + bandId!!.toInt()
    }

    fun getUserId() = this.userId

    fun getBandId() = this.bandId

}
