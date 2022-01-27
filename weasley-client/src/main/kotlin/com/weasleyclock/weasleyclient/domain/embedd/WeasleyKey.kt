package com.weasleyclock.weasleyclient.domain.embedd

import org.hibernate.Hibernate
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class WeasleyKey : Serializable {

    @Transient
    private val serialVersionUID = 1L

    @Column
    var userId: Long? = null

    @Column(nullable = false)
    var title: String? = null

    constructor()

    constructor(userId: Long, title: String) {
        this.userId = userId
        this.title = title
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as WeasleyKey
        return anyOther.userId == this.userId && anyOther.title!! == this.title
    }

    override fun hashCode(): Int {
        return userId.hashCode() + title.hashCode();
    }

}
