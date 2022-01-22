package com.weasleyclock.weasley.domain

import com.weasleyclock.weasley.domain.base.BaseEntity
import org.hibernate.Hibernate
import org.springframework.beans.factory.support.ManagedSet
import javax.persistence.*

@Table
@Entity
class Band : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(nullable = false)
    var title: String? = null

    @OneToMany(mappedBy = "band", cascade = [CascadeType.ALL])
    var memberSet: MutableSet<Member> = ManagedSet()

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

}
