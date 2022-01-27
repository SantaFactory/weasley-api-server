package com.weasleyclock.weasleyclient.domain

import org.hibernate.Hibernate
import javax.persistence.*

@Table
@Entity
class Token {

    @Id
    var userId: Long? = null

    @Column(name = "token_value", columnDefinition = "TEXT")
    var token: String? = null

    constructor()

    constructor(userId: Long, token: String) {
        this.userId = userId
        this.token = token
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        val anyOther = other as Token
        return anyOther.userId === this.userId
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }
}
