package com.weasleyclock.weasley.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.Table

@Table
@Entity
class Token {

    @Id
    var userId: Long? = null

    // todo : blob change
    @Lob
    var token: String? = null

    constructor()

    constructor(userId: Long, token: String) {
        this.userId = userId
        this.token = token
    }

}
