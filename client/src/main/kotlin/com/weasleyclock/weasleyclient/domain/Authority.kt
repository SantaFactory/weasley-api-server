package com.weasleyclock.weasleyclient.domain

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table(name = "auth")
@Entity
class Authority {

    @Id
    var title: String? = null

    constructor()

    constructor(title: String) {
        this.title = title
    }

    override fun hashCode(): Int {
        return title.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val authority = other as Authority
        return authority.title.equals(this.title)
    }

}
