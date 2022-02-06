package com.weasleyclock.weasleyclient.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.weasleyclock.weasleyclient.domain.base.BaseEntity
import org.hibernate.Hibernate
import org.springframework.beans.factory.support.ManagedSet
import javax.persistence.*

@Table
@Entity
class Band : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long? = null

    @Column(nullable = false)
    private var title: String? = null

    @JsonIgnore
    @OneToMany(
        mappedBy = "band", fetch = FetchType.LAZY, cascade = [CascadeType.PERSIST, CascadeType.REMOVE]
    )
    private var memberSet: MutableSet<Member> = ManagedSet()

    constructor(title: String) {
        this.title = title
    }

    constructor()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as Band
        return anyOther.id == this.id
    }

    override fun hashCode(): Int {
        return id!!.toInt()
    }

    @Transient
    fun updateBandTitle(title: String) {
        this.title = title
    }

    @Transient
    fun changeBandMember(memberSet: MutableSet<Member>) {
        this.memberSet = memberSet
    }

    @Transient
    fun getId() = this.id

    @Transient
    fun getTitle() = this.title

    @Transient
    fun getMemberSet() = this.memberSet

    @Transient
    fun setId(id: Long): Band {
        this.id = id
        return this
    }

    fun id(id: Long): Band {
        this.id = id
        return this
    }

    fun memberSet(memberSet: MutableSet<Member>): Band {
        this.memberSet = memberSet
        return this
    }
}
