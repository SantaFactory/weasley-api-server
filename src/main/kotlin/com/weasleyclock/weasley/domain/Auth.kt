package com.weasleyclock.weasley.domain

import org.hibernate.Hibernate
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table


@Table
@Entity
class Auth {

    @Id
    var title: String? = null

    constructor()

    constructor(title: String) {
        this.title = title
    }

    public override fun hashCode(): Int {
        return title.hashCode();
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val auth = other as Auth
        return auth.title.equals(this.title)
    }

}
